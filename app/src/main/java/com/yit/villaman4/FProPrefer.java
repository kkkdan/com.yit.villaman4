package com.yit.villaman4;
import android.content.Context;

public class FProPrefer {

	private static FProPrefer SINGLE_U;
	private static PProPerfer SINGLE_W;

	private FProPrefer() {
	}

	public static PProPerfer with(Context pCon) {
		if (SINGLE_U == null) {
			SINGLE_U = new FProPrefer();
		}
		if (SINGLE_W == null) {
			SINGLE_W = SINGLE_U.get(pCon);
		}
		return SINGLE_W;
	}

	private PProPerfer get(Context pCon) {
		return new PProPerfer(pCon);
	}

	public class PProPerfer extends PPreference {
		private PProPerfer(Context pCon) {
			super(pCon, "WProPerfer");
		}
		
		public String getSearchText() {
			return read("searchText");
		}
		public void setSearchText(String searchText) {
			write("searchText", searchText);
		}

		public String getVilOldNew() {return read("viloldnew");}
		public void setVilOldNew(String viloldnew) {	write("viloldnew", viloldnew);}

		public String getPoiType() {return read("poitype");}
		public void setPoiType(String poitype) {	write("poitype", poitype);}

		public String getPassWd() {
			return read("passwd");
		}
		public void setPassWd(String passwd) {	write("passwd", passwd);}

		public String getSelectID() { return read("selectID");	}
		public void setSelectID(String selectID) {	write("selectID", selectID);}

		//////////////////////////
		public String getSi() {
			return read("si");
		}
		public void setSi(String si) {	write("si", si);}

		public String getGu() {
			return read("gu");
		}
		public void setGu(String gu) {
			write("gu", gu);
		}

		public String getDong() {
			return read("dong");
		}
		public void setDong(String dong) {
			write("dong", dong);
		}


		////////////////////////////////////////
		public int getMinInputBo() {return read("minInputBo", 0);}
		public void setMinInputBo(int minInputBo) {	write("minInputBo", minInputBo);}

		public int getMaxInputBo() {
			return read("maxInputBo", 0);
		}
		public void setMaxInputBo(int maxInputBo) {
			write("maxInputBo", maxInputBo);
		}

		public int getMinInputMonth() {return read("minInputMonth", 0);}
		public void setMinInputMonth(int minInputMonth) {	write("minInputMonth", minInputMonth);}

		public int getMaxInputMonth() {
			return read("maxInputMonth", 0);
		}
		public void setMaxInputMonth(int maxInputMonth) {
			write("maxInputMonth", maxInputMonth);
		}

		public int getMinArea() {
			return read("minArea", 0);
		}
		public void setMinArea(int minArea) {
			write("minArea", minArea);
		}

		public int getMaxArea() {
			return read("maxArea", 0);
		}
		public void setMaxArea(int maxArea) {
			write("maxArea", maxArea);
		}

		public int getMinFloor() {
			return read("minFloor", 0);
		}
		public void setMinFloor(int minFloor) {
			write("minFloor", minFloor);
		}

		public int getMaxFloor() {
			return read("maxFloor", 0);
		}
		public void setMaxFloor(int maxFloor) {
			write("maxFloor", maxFloor);
		}

		public int getMinRoom() {
			return read("minRoom", 0);
		}
		public void setMinRoom(int minRoom) {
			write("minRoom", minRoom);
		}

		public int getMaxRoom() {
			return read("maxRoom", 0);
		}
		public void setMaxRoom(int maxRoom) {
			write("maxRoom", maxRoom);
		}

		public int getMinYear() {
			return read("minYear", 0);
		}
		public void setMinYear(int minYear) {
			write("minYear", minYear);
		}

		public int getMaxYear() {
			return read("maxYear", 0);
		}
		public void setMaxYear(int maxYear) {	write("maxYear", maxYear); }

		public String getMyNew02() {return read("MyNew02");}
		public void setMyNew02(String MyNew02) {
			write("MyNew02", MyNew02);
		}

		public String getMyBlankPicyn() {	return read("MyBlankPicyn");	}
		public void setMyBlankPicyn(String MyBlankPicyn) {
			write("MyBlankPicyn", MyBlankPicyn);
		}

		public String getMyConPicyn() {	return read("MyConPicyn");	}
		public void setMyConPicyn(String MyConPicyn) {
			write("MyConPicyn", MyConPicyn);
		}
		public String getMyConEmpty() {	return read("MyConEmpty");	}
		public void setMyConEmpty(String MyConEmpty) {	write("MyConEmpty", MyConEmpty); }

		public String getMyNewPicyn() {	return read("MyNewPicyn");	}
		public void setMyNewPicyn(String MyNewPicyn) {
			write("MyNewPicyn", MyNewPicyn);
		}
		public String getMyNewEmpty() {	return read("MyNewEmpty");	}
		public void setMyNewEmpty(String MyNewEmpty) {	write("MyNewEmpty", MyNewEmpty); }

		public String getMyConfirm02() {
			return read("MyConfirm02");
		}
		public void setMyConfirm02(String MyConfirm02) {
			write("MyConfirm02", MyConfirm02);
		}

		public String getChoiceMaSearch() {
			return read("ma_search");
		}
		public void setChoiceMaSearch(String ma_search) {write("ma_search", ma_search);	}

		public String getSearchAction() {
			return read("searchaction");
		}
		public void setSearchAction(String searchaction) {write("searchaction", searchaction);	}


		public String getEnv_distance() {return read("Env_distance");}
		public void setEnv_distance(String Env_distance) {
			write("Env_distance", Env_distance);
		}





	}
}
