package multi_image_selector;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.yit.villaman4.R;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.appcompat.widget.ListPopupWindow;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

@TargetApi(Build.VERSION_CODES.FROYO)
public class MultiImageSelectorFragment extends Fragment {

	public static final String	EXTRA_SELECT_COUNT			= "max_select_count";
	public static final String	EXTRA_SELECT_MODE			= "select_count_mode";
	public static final String	EXTRA_SHOW_CAMERA			= "show_camera";
	public static final String	EXTRA_DEFAULT_SELECTED_LIST	= "default_result";
	public static final int		MODE_SINGLE					= 0;
	public static final int		MODE_MULTI					= 1;
	private static final int	LOADER_ALL					= 0;
	private static final int	LOADER_CATEGORY				= 1;
	private static final int	REQUEST_CAMERA				= 100;

	private ArrayList<String>	resultList					= new ArrayList<String>();
	private ArrayList<Folder>	mResultFolder				= new ArrayList<Folder>();

	private GridView			mGridView;
	private Callback			mCallback;

	private ImageGridAdapter	mImageAdapter;
	private FolderAdapter		mFolderAdapter;
	private ListPopupWindow		mFolderPopupWindow;

	private TextView			mTimeLineText;
	private TextView			mCategoryText;
	private Button				mPreviewBtn;
	private View				mPopupAnchorView;
	private int					mDesireImageCount;
	private boolean				hasFolderGened				= false;
	private File				mTmpFile;

	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mCallback = (Callback) activity;
		}
		catch(ClassCastException e) {
			throw new ClassCastException("The Activity must implement MultiImageSelectorFragment.Callback interface...");
		}
	}

	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.list_item_multi_image, container, false);
	}

	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		mDesireImageCount = getArguments().getInt(EXTRA_SELECT_COUNT);

		final int mode = getArguments().getInt(EXTRA_SELECT_MODE);

		if(mode == MODE_MULTI) {
			ArrayList<String> tmp = getArguments().getStringArrayList(EXTRA_DEFAULT_SELECTED_LIST);
			if(tmp != null && tmp.size() > 0) {
				resultList = tmp;
			}
		}

		final boolean showCamera = getArguments().getBoolean(EXTRA_SHOW_CAMERA, true);
		mImageAdapter = new ImageGridAdapter(getActivity(), showCamera);
		mImageAdapter.showSelectIndicator(mode == MODE_MULTI);

		if(showCamera) {
			mTmpFile = FileUtils.createTmpFile(getActivity());
		}

		mPopupAnchorView = view.findViewById(R.id.footer);

		mTimeLineText = (TextView) view.findViewById(R.id.timeline_area);
		mTimeLineText.setVisibility(View.GONE);

		mCategoryText = (TextView) view.findViewById(R.id.category_btn);
		mCategoryText.setText("모든 사진");
		mCategoryText.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				if(mFolderPopupWindow.isShowing()) {
					mFolderPopupWindow.dismiss();
				}
				else {
					mFolderPopupWindow.show();
					int index = mFolderAdapter.getSelectIndex();
					index = index == 0 ? index : index - 1;
					mFolderPopupWindow.getListView().setSelection(index);
				}
			}
		});

		mPreviewBtn = (Button) view.findViewById(R.id.preview);
		if(resultList == null || resultList.size() <= 0) {
			mPreviewBtn.setText("시사");
			mPreviewBtn.setEnabled(false);
		}
		mPreviewBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {}
		});

		mGridView = (GridView) view.findViewById(R.id.grid);
		mGridView.setOnScrollListener(new AbsListView.OnScrollListener() {
			public void onScrollStateChanged(AbsListView absListView, int state) {
				if(state == SCROLL_STATE_IDLE) {
					mTimeLineText.setVisibility(View.GONE);
				}
				else if(state == SCROLL_STATE_FLING) {
					mTimeLineText.setVisibility(View.VISIBLE);
				}
			}

			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				if(mTimeLineText.getVisibility() == View.VISIBLE) {
					int index = firstVisibleItem + 1 == view.getAdapter().getCount() ? view.getAdapter().getCount() - 1 : firstVisibleItem + 1;
					Image image = (Image) view.getAdapter().getItem(index);
					if(image != null) {
						mTimeLineText.setText(TimeUtils.formatPhotoDate(image.path));
					}
				}
			}
		});
		mGridView.setAdapter(mImageAdapter);
		mGridView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
			@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
			public void onGlobalLayout() {
				final int numCount = mGridView.getNumColumns();
				final int columnSpace = getResources().getDimensionPixelOffset(R.dimen.space_size);
				int columnWidth = (mGridView.getWidth() - columnSpace * (numCount - 1)) / numCount;
				mImageAdapter.setItemSize(columnWidth);
				if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
					mGridView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
				}
				else {
					mGridView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
				}
			}
		});
		mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				if(mImageAdapter.isShowCamera()) {
					if(i == 0) {
						showCameraAction();
					}
					else {
						Image image = (Image) adapterView.getAdapter().getItem(i);
						selectImageFromGrid(image, mode);
					}
				}
				else {
					Image image = (Image) adapterView.getAdapter().getItem(i);
					selectImageFromGrid(image, mode);
				}
			}
		});

		mFolderAdapter = new FolderAdapter(getActivity());
		createPopupFolderList();
	}

	private void createPopupFolderList() {
		mFolderPopupWindow = new ListPopupWindow(getActivity());
		mFolderPopupWindow.setAdapter(mFolderAdapter);
		int sHeightPix = getResources().getDisplayMetrics().heightPixels;
		int sWidthPix = getResources().getDisplayMetrics().widthPixels;
		mFolderPopupWindow.setContentWidth(sWidthPix / 2);

		mFolderPopupWindow.setHeight(sHeightPix / 2);
		mFolderPopupWindow.setWidth(sWidthPix / 2);

		mFolderPopupWindow.setAnchorView(mPopupAnchorView);
		mFolderPopupWindow.setModal(true);

		mFolderPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				if(i == 0) {
					getActivity().getSupportLoaderManager().restartLoader(LOADER_ALL, null, mLoaderCallback);
					mCategoryText.setText("모든 사진");
					mImageAdapter.setShowCamera(true);
				}
				else {
					Folder folder = (Folder) adapterView.getAdapter().getItem(i);
					if(null != folder) {
						Bundle args = new Bundle();
						args.putString("path", folder.path);
						getActivity().getSupportLoaderManager().restartLoader(LOADER_CATEGORY, args, mLoaderCallback);
						mCategoryText.setText(folder.name);
					}
					mImageAdapter.setShowCamera(false);
				}
				mFolderAdapter.setSelectIndex(i);
				mFolderPopupWindow.dismiss();

				mGridView.smoothScrollToPosition(0);
			}
		});
		
		mFolderPopupWindow.postShow();
	}

	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		getActivity().getSupportLoaderManager().initLoader(LOADER_ALL, null, mLoaderCallback);
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == Activity.RESULT_OK) {
			if(requestCode == REQUEST_CAMERA) {
				if(mTmpFile != null) {
					if(mCallback != null) {
						mCallback.onCameraShot(mTmpFile);
					}
				}
			}
		}
	}

	private void showCameraAction() {
		Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		if(cameraIntent.resolveActivity(getActivity().getPackageManager()) != null) {
			cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mTmpFile));
			startActivityForResult(cameraIntent, REQUEST_CAMERA);
		}
	}

	private void selectImageFromGrid(Image image, int mode) {
		if(image != null) {
			if(mode == MODE_MULTI) {
				if(resultList.contains(image.path)) {
					resultList.remove(image.path);
					if(resultList.size() != 0) {
						mPreviewBtn.setEnabled(true);
						mPreviewBtn.setText("시사(" + resultList.size() + ")");
					}
					else {
						mPreviewBtn.setEnabled(false);
						mPreviewBtn.setText("시사");
					}
					if(mCallback != null) {
						mCallback.onImageUnselected(image.path);
					}
				}
				else {
					if(mDesireImageCount == resultList.size()) {
						return;
					}

					resultList.add(image.path);
					mPreviewBtn.setEnabled(true);
					mPreviewBtn.setText("시사(" + resultList.size() + ")");
					if(mCallback != null) {
						mCallback.onImageSelected(image.path);
					}
				}
				mImageAdapter.select(image);
			}
			else if(mode == MODE_SINGLE) {
				if(mCallback != null) {
					mCallback.onSingleImageSelected(image.path);
				}
			}
		}
	}

	private LoaderManager.LoaderCallbacks<Cursor>	mLoaderCallback	= new LoaderManager.LoaderCallbacks<Cursor>() {

		// private final String[]	IMAGE_PROJECTION	= { MediaStore.Images.Media.DATA, MediaStore.Images.Media.DISPLAY_NAME, MediaStore.Images.Media.DATE_ADDED, MediaStore.Images.Media._ID };
		private final String[]	VIDEO_PROJECTION	= { MediaStore.Video.Media.DATA, MediaStore.Video.Media.DISPLAY_NAME, MediaStore.Video.Media.DATE_ADDED, MediaStore.Video.Media._ID };

		public Loader<Cursor> onCreateLoader(int id, Bundle args) {

			if(id == LOADER_ALL) {

				String selection = MediaStore.Files.FileColumns.MEDIA_TYPE + "="
						+ MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE
						+ " OR "
						+ MediaStore.Files.FileColumns.MEDIA_TYPE + "="
						+ MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO;

				Uri queryUri = MediaStore.Files.getContentUri("external");

				CursorLoader cursorLoader = new CursorLoader(
						getActivity(),
						queryUri,
						VIDEO_PROJECTION,
						selection,
						null, // Selection args (none).
						MediaStore.Files.FileColumns.DATE_ADDED + " DESC" // Sort order.
				);
				return cursorLoader;

			}
			/* else if(id == LOADER_CATEGORY) {
				CursorLoader cursorLoader = new CursorLoader(getActivity(), MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_PROJECTION, IMAGE_PROJECTION[0] + " like '%" + args.getString("path") + "%'", null, IMAGE_PROJECTION[2] + " DESC");
				return cursorLoader;
			} */

			return null;
		}




		public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
			if(data != null) {
				List<Image> images = new ArrayList<Image>();
				int count = data.getCount();
				if(count > 0) {
					data.moveToFirst();
					do {

						String path = data.getString(data.getColumnIndexOrThrow(VIDEO_PROJECTION[0]));
						String name = data.getString(data.getColumnIndexOrThrow(VIDEO_PROJECTION[1]));
						long dateTime = data.getLong(data.getColumnIndexOrThrow(VIDEO_PROJECTION[2]));

						Image image = new Image(path, name, dateTime);
						// Video
						images.add(image);
						if(!hasFolderGened) {
							File imageFile = new File(path);
							File folderFile = imageFile.getParentFile();
							Folder folder = new Folder();
							folder.name = folderFile.getName();
							folder.path = folderFile.getAbsolutePath();
							folder.cover = image;
							if(!mResultFolder.contains(folder)) {
								List<Image> imageList = new ArrayList<Image>();
								imageList.add(image);
								folder.images = imageList;
								mResultFolder.add(folder);
							}
							else {
								Folder f = mResultFolder.get(mResultFolder.indexOf(folder));
								f.images.add(image);
							}
						}

					}
					while(data.moveToNext());

					mImageAdapter.setData(images);

					if(resultList != null && resultList.size() > 0) {
						mImageAdapter.setDefaultSelected(resultList);
					}

					mFolderAdapter.setData(mResultFolder);
					hasFolderGened = true;

				}
				else {
					System.out.println("no image found");
				}
			}
		}

		public void onLoaderReset(Loader<Cursor> loader) {

		}
	};

	public interface Callback {
		public void onSingleImageSelected(String path);

		public void onImageSelected(String path);

		public void onImageUnselected(String path);

		public void onCameraShot(File imageFile);
	}

}