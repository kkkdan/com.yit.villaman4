package multi_image_selector;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.bumptech.glide.Glide;
import com.yit.villaman4.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FolderAdapter extends BaseAdapter {

	private Context			mContext;
	private LayoutInflater	mInflater;
	private List<Folder>	mFolders		= new ArrayList<Folder>();
	int						mImageSize;
	int						lastSelected	= 0;

	public FolderAdapter(Context context) {
		mContext = context;
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mImageSize = mContext.getResources().getDimensionPixelOffset(R.dimen.folder_cover_size);
	}

	public void setData(List<Folder> folders) {
		if(folders != null && folders.size() > 0) {
			mFolders = folders;
		}
		else {
			mFolders.clear();
		}
		notifyDataSetChanged();
	}

	public int getCount() {
		return mFolders.size() + 1;
	}

	public Folder getItem(int i) {
		if(i == 0) return null;
		return mFolders.get(i - 1);
	}

	public long getItemId(int i) {
		return i;
	}

	public View getView(int i, View view, ViewGroup viewGroup) {
		ViewHolder holder;
		if(view == null) {
			view = mInflater.inflate(R.layout.list_item_folder, viewGroup, false);
			holder = new ViewHolder(view);
		}
		else {
			holder = (ViewHolder) view.getTag();
		}
		if(holder != null) {
			if(i == 0) {
				holder.name.setText("모든 사진");
				holder.size.setText(getTotalImageSize() + "장");
				if(mFolders.size() > 0) {
					Folder f = mFolders.get(0);
					Glide.with(mContext).load(new File(f.cover.path)).error(R.drawable.default_error).override(mImageSize, mImageSize).centerCrop().into(holder.cover);
				}
			}
			else {
				holder.bindData(getItem(i));
			}
			if(lastSelected == i) {
				holder.indicator.setVisibility(View.VISIBLE);
			}
			else {
				holder.indicator.setVisibility(View.GONE);
			}
		}
		return view;
	}

	public int getTotalImageSize() {
		int result = 0;
		if(mFolders != null && mFolders.size() > 0) {
			for(Folder f : mFolders) {
				result += f.images.size();
			}
		}
		return result;
	}

	public void setSelectIndex(int i) {
		if(lastSelected == i) return;

		lastSelected = i;
		notifyDataSetChanged();
	}

	public int getSelectIndex() {
		return lastSelected;
	}

	class ViewHolder {
		ImageView	cover;
		TextView	name;
		TextView	size;
		ImageView	indicator;

		ViewHolder(View view) {
			cover = (ImageView) view.findViewById(R.id.cover);
			name = (TextView) view.findViewById(R.id.name);
			size = (TextView) view.findViewById(R.id.size);
			indicator = (ImageView) view.findViewById(R.id.indicator);
			view.setTag(this);
		}

		void bindData(Folder data) {
			name.setText(data.name);
			size.setText(data.images.size() + "장");
			Glide.with(mContext).load(new File(data.cover.path)).placeholder(R.drawable.default_error).override(mImageSize, mImageSize).centerCrop().into(cover);
		}
	}

}
