package com.dev.naveen.movieapp.dto.trailers;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;


public class Trailers implements Parcelable{

	private int id;
	private List<ResultsItem> results;

	public Trailers(){}

	protected Trailers(Parcel in) {
		id = in.readInt();
		results = in.createTypedArrayList(ResultsItem.CREATOR);
	}

	public static final Creator<Trailers> CREATOR = new Creator<Trailers>() {
		@Override
		public Trailers createFromParcel(Parcel in) {
			return new Trailers(in);
		}

		@Override
		public Trailers[] newArray(int size) {
			return new Trailers[size];
		}
	};

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setResults(List<ResultsItem> results){
		this.results = results;
	}

	public List<ResultsItem> getResults(){
		return results;
	}

	@Override
 	public String toString(){
		return 
			"Trailers{" + 
			"id = '" + id + '\'' + 
			",results = '" + results + '\'' + 
			"}";
		}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(id);
		dest.writeTypedList(results);
	}
}