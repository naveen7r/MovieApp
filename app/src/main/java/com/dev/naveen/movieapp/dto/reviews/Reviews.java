package com.dev.naveen.movieapp.dto.reviews;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Reviews implements Parcelable{
	private int id;
	private int page;
	private int totalPages;
	private List<ResultsItem> results;
	private int totalResults;

	public Reviews(){

	}

	public Reviews(Parcel in) {
		id = in.readInt();
		page = in.readInt();
		totalPages = in.readInt();
		results = in.createTypedArrayList(ResultsItem.CREATOR);
		totalResults = in.readInt();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(id);
		dest.writeInt(page);
		dest.writeInt(totalPages);
		dest.writeTypedList(results);
		dest.writeInt(totalResults);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public static final Creator<Reviews> CREATOR = new Creator<Reviews>() {
		@Override
		public Reviews createFromParcel(Parcel in) {
			return new Reviews(in);
		}

		@Override
		public Reviews[] newArray(int size) {
			return new Reviews[size];
		}
	};

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setPage(int page){
		this.page = page;
	}

	public int getPage(){
		return page;
	}

	public void setTotalPages(int totalPages){
		this.totalPages = totalPages;
	}

	public int getTotalPages(){
		return totalPages;
	}

	public void setResults(List<ResultsItem> results){
		this.results = results;
	}

	public List<ResultsItem> getResults(){
		return results;
	}

	public void setTotalResults(int totalResults){
		this.totalResults = totalResults;
	}

	public int getTotalResults(){
		return totalResults;
	}

	@Override
 	public String toString(){
		return 
			"Reviews{" + 
			"id = '" + id + '\'' + 
			",page = '" + page + '\'' + 
			",total_pages = '" + totalPages + '\'' + 
			",results = '" + results + '\'' + 
			",total_results = '" + totalResults + '\'' + 
			"}";
		}
}