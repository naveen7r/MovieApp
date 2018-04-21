package com.dev.naveen.movieapp.dto;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class MovieResponse implements Parcelable{
	private long page;
	private long total_pages;
	private List<ResultsItem> results;
	private long total_results;

	protected MovieResponse(Parcel in) {
		page = in.readLong();
		total_pages = in.readLong();
		results = in.createTypedArrayList(ResultsItem.CREATOR);
		total_results = in.readLong();
	}

	public static final Creator<MovieResponse> CREATOR = new Creator<MovieResponse>() {
		@Override
		public MovieResponse createFromParcel(Parcel in) {
			return new MovieResponse(in);
		}

		@Override
		public MovieResponse[] newArray(int size) {
			return new MovieResponse[size];
		}
	};

	public void setPage(long page){
		this.page = page;
	}

	public long getPage(){
		return page;
	}

	public void setTotal_pages(long total_pages){
		this.total_pages = total_pages;
	}

	public long getTotal_pages(){
		return total_pages;
	}

	public void setResults(List<ResultsItem> results){
		this.results = results;
	}

	public List<ResultsItem> getResults(){
		return results;
	}

	public void setTotal_results(long total_results){
		this.total_results = total_results;
	}

	public long getTotal_results(){
		return total_results;
	}

	@Override
 	public String toString(){
		return 
			"MovieResponse{" + 
			"page = '" + page + '\'' + 
			",total_pages = '" + total_pages + '\'' +
			",results = '" + results + '\'' + 
			",total_results = '" + total_results + '\'' +
			"}";
		}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(page);
		dest.writeLong(total_pages);
		dest.writeTypedList(results);
		dest.writeLong(total_results);
	}
}