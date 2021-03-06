package com.dev.naveen.movieapp.dto;

import android.arch.lifecycle.ViewModel;
import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.List;

@Entity(tableName = "movie")
public class ResultsItem implements Parcelable {
	@ColumnInfo(name = "overview")
	private String overview;
	private String original_language;
	private String original_title;
	private boolean video;
	@ColumnInfo(name = "title")
	private String title;

	@ColumnInfo(name = "poster_path")
	private String poster_path;
	private String backdrop_path;
	@ColumnInfo(name = "release_date")
	private String release_date;
	@ColumnInfo(name = "vote_average")
	private Double vote_average;
	private Double popularity;
	@PrimaryKey
	@NonNull
	@ColumnInfo(name = "movieId")
	private Long id;
	private boolean adult;
	private Long vote_count;

	public ResultsItem() {

	}

	public ResultsItem(String overview, String original_language, String original_title, boolean video, String title, String poster_path, String backdrop_path, String release_date, Double vote_average, Double popularity, @NonNull Long id, boolean adult, Long vote_count) {
		this.overview = overview;
		this.original_language = original_language;
		this.original_title = original_title;
		this.video = video;
		this.title = title;
		this.poster_path = poster_path;
		this.backdrop_path = backdrop_path;
		this.release_date = release_date;
		this.vote_average = vote_average;
		this.popularity = popularity;
		this.id = id;
		this.adult = adult;
		this.vote_count = vote_count;
	}

	protected ResultsItem(Parcel in) {
		overview = in.readString();
		original_language = in.readString();
		original_title = in.readString();
		video = in.readByte() != 0;
		title = in.readString();
		poster_path = in.readString();
		backdrop_path = in.readString();
		release_date = in.readString();
		if (in.readByte() == 0) {
			vote_average = null;
		} else {
			vote_average = in.readDouble();
		}
		if (in.readByte() == 0) {
			popularity = null;
		} else {
			popularity = in.readDouble();
		}
		if (in.readByte() == 0) {
			id = null;
		} else {
			id = in.readLong();
		}
		adult = in.readByte() != 0;
		if (in.readByte() == 0) {
			vote_count = null;
		} else {
			vote_count = in.readLong();
		}
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

	public void setOverview(String overview){
		this.overview = overview;
	}

	public String getOverview(){
		return overview;
	}

	public void setOriginal_language(String original_language){
		this.original_language = original_language;
	}

	public String getOriginal_language(){
		return original_language;
	}

	public void setOriginal_title(String original_title){
		this.original_title = original_title;
	}

	public String getOriginal_title(){
		return original_title;
	}

	public void setVideo(boolean video){
		this.video = video;
	}

	public boolean isVideo(){
		return video;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}



	public void setPoster_path(String poster_path){
		this.poster_path = poster_path;
	}

	public String getPoster_path(){
		return poster_path;
	}

	public void setBackdrop_path(String backdrop_path){
		this.backdrop_path = backdrop_path;
	}

	public String getBackdrop_path(){
		return backdrop_path;
	}

	public void setRelease_date(String release_date){
		this.release_date = release_date;
	}

	public String getRelease_date(){
		return release_date;
	}

	public void setVote_average(Double vote_average){
		this.vote_average = vote_average;
	}

	public Double getVote_average(){
		return vote_average;
	}

	public void setPopularity(Double popularity){
		this.popularity = popularity;
	}

	public Double getPopularity(){
		return popularity;
	}

	public void setId(Long id){
		this.id = id;
	}

	public Long getId(){
		return id;
	}

	public void setAdult(boolean adult){
		this.adult = adult;
	}

	public boolean isAdult(){
		return adult;
	}

	public void setVote_count(Long vote_count){
		this.vote_count = vote_count;
	}

	public Long getVote_count(){
		return vote_count;
	}

	@Override
 	public String toString(){
		return
			"ResultsItem{" +
			"overview = '" + overview + '\'' +
			",original_language = '" + original_language + '\'' +
			",original_title = '" + original_title + '\'' +
			",video = '" + video + '\'' +
			",title = '" + title + '\'' +
			",poster_path = '" + poster_path + '\'' +
			",backdrop_path = '" + backdrop_path + '\'' +
			",release_date = '" + release_date + '\'' +
			",vote_average = '" + vote_average + '\'' +
			",popularity = '" + popularity + '\'' +
			",id = '" + id + '\'' +
			",adult = '" + adult + '\'' +
			",vote_count = '" + vote_count + '\'' +
			"}";
		}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(overview);
		dest.writeString(original_language);
		dest.writeString(original_title);
		dest.writeByte((byte) (video ? 1 : 0));
		dest.writeString(title);
		dest.writeString(poster_path);
		dest.writeString(backdrop_path);
		dest.writeString(release_date);
		if (vote_average == null) {
			dest.writeByte((byte) 0);
		} else {
			dest.writeByte((byte) 1);
			dest.writeDouble(vote_average);
		}
		if (popularity == null) {
			dest.writeByte((byte) 0);
		} else {
			dest.writeByte((byte) 1);
			dest.writeDouble(popularity);
		}
		if (id == null) {
			dest.writeByte((byte) 0);
		} else {
			dest.writeByte((byte) 1);
			dest.writeLong(id);
		}
		dest.writeByte((byte) (adult ? 1 : 0));
		if (vote_count == null) {
			dest.writeByte((byte) 0);
		} else {
			dest.writeByte((byte) 1);
			dest.writeLong(vote_count);
		}
	}
}