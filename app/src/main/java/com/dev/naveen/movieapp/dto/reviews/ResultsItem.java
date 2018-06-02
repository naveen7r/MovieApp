package com.dev.naveen.movieapp.dto.reviews;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

@Entity(tableName = "reviews")
public class ResultsItem implements Parcelable{
	@ColumnInfo(name = "author")
	private String author;
	@NonNull
	@PrimaryKey
	private String id;
	@ColumnInfo(name = "content")
	private String content;
	private String url;

	@ColumnInfo(name = "review_id")
	private String r_id;

	public ResultsItem() {

	}

	public ResultsItem(String author, String id, String content, String url, String r_id) {
		this.author = author;
		this.id = id;
		this.content = content;
		this.url = url;
		this.r_id = r_id;
	}

	protected ResultsItem(Parcel in) {
		author = in.readString();
		id = in.readString();
		content = in.readString();
		url = in.readString();
		r_id = in.readString();
	}

	public static final Creator<ResultsItem> CREATOR = new Creator<ResultsItem>() {
		@Override
		public ResultsItem createFromParcel(Parcel in) {
			return new ResultsItem(in);
		}

		@Override
		public ResultsItem[] newArray(int size) {
			return new ResultsItem[size];
		}
	};

	public String getR_id() {
		return r_id;
	}

	public void setR_id(String r_id) {
		this.r_id = r_id;
	}

	public void setAuthor(String author){
		this.author = author;
	}

	public String getAuthor(){
		return author;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setContent(String content){
		this.content = content;
	}

	public String getContent(){
		return content;
	}

	public void setUrl(String url){
		this.url = url;
	}

	public String getUrl(){
		return url;
	}

	@Override
 	public String toString(){
		return 
			"ResultsItem{" + 
			"author = '" + author + '\'' + 
			",id = '" + id + '\'' + 
			",content = '" + content + '\'' + 
			",url = '" + url + '\'' + 
			"}";
		}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(author);
		dest.writeString(id);
		dest.writeString(content);
		dest.writeString(url);
		dest.writeString(r_id);
	}
}
