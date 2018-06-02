package com.dev.naveen.movieapp.dto.trailers;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

@Entity(tableName = "trailer")
public class ResultsItem implements Parcelable{
	@ColumnInfo(name = "site")
	private String site;
	private int size;
	private String iso31661;
	@ColumnInfo(name = "name")
	private String name;
	@NonNull
	@PrimaryKey
	private String id;
	@ColumnInfo(name = "type")
	private String type;
	private String iso6391;
	@ColumnInfo(name = "key")
	private String key;

	@ColumnInfo(name = "trailer_id")
	private String t_id;

	public ResultsItem() {

	}

	public ResultsItem(String site, int size, String iso31661, String name, String id, String type, String iso6391, String key, String t_id) {
		this.site = site;
		this.size = size;
		this.iso31661 = iso31661;
		this.name = name;
		this.id = id;
		this.type = type;
		this.iso6391 = iso6391;
		this.key = key;
		this.t_id = t_id;
	}

	protected ResultsItem(Parcel in) {
		site = in.readString();
		size = in.readInt();
		iso31661 = in.readString();
		name = in.readString();
		id = in.readString();
		type = in.readString();
		iso6391 = in.readString();
		key = in.readString();
		t_id = in.readString();
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

	public String getT_id() {
		return t_id;
	}

	public void setT_id(String t_id) {
		this.t_id = t_id;
	}

	public void setSite(String site){
		this.site = site;
	}

	public String getSite(){
		return site;
	}

	public void setSize(int size){
		this.size = size;
	}

	public int getSize(){
		return size;
	}

	public void setIso31661(String iso31661){
		this.iso31661 = iso31661;
	}

	public String getIso31661(){
		return iso31661;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return type;
	}

	public void setIso6391(String iso6391){
		this.iso6391 = iso6391;
	}

	public String getIso6391(){
		return iso6391;
	}

	public void setKey(String key){
		this.key = key;
	}

	public String getKey(){
		return key;
	}

	@Override
 	public String toString(){
		return 
			"ResultsItem{" + 
			"site = '" + site + '\'' + 
			",size = '" + size + '\'' + 
			",iso_3166_1 = '" + iso31661 + '\'' + 
			",name = '" + name + '\'' + 
			",id = '" + id + '\'' + 
			",type = '" + type + '\'' + 
			",iso_639_1 = '" + iso6391 + '\'' + 
			",key = '" + key + '\'' + 
			"}";
		}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(site);
		dest.writeInt(size);
		dest.writeString(iso31661);
		dest.writeString(name);
		dest.writeString(id);
		dest.writeString(type);
		dest.writeString(iso6391);
		dest.writeString(key);
		dest.writeString(t_id);
	}
}
