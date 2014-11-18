package com.example.myfitnessapplication;

import java.sql.Date;
import java.text.SimpleDateFormat;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.ParseException;


public class DeporteOperations {
	private SQLiteDatabase db;
	private DeporteHelper dbHelper;
	public static final String TABLE_DEPORTES="deportes";
	public static final String COLUMN_ID="_id";
	public static final String COLUMN_DEPORTE="deporte";
	public static final String COLUMN_TIEMPO="tiempo";
	public static final String COLUMN_FECHA="fecha";
	public static final String COLUMN_CALORIAS="calorias";
	public DeporteOperations(Context context){
		dbHelper=new DeporteHelper(context);
	}
	public void open()throws SQLException{
		db=dbHelper.getWritableDatabase();
	}
	
	public void addDeporte(Deporte deporte){
		ContentValues values=new ContentValues();
		//values.put(COLUMN_NAME, pelicula.getName());
		//values.put(COLUMN_RANKING, pelicula.getRanking());
		values.put(COLUMN_DEPORTE, deporte.getDeporte());
		values.put(COLUMN_FECHA, deporte.getFecha());
		values.put(COLUMN_CALORIAS, deporte.getCalorias());
		values.put(COLUMN_TIEMPO, deporte.getTiempo());
		db.insert(TABLE_DEPORTES, null, values);
		
	}
	
	public Deporte findDeporte(String nombreDeporte){
		String query= "Select * FROM "+TABLE_DEPORTES+" WHERE "+COLUMN_DEPORTE+" = \""+nombreDeporte+"\"" ;
		Cursor cursor=db.rawQuery(query, null);
		Deporte deporte=new Deporte();
		if (cursor.moveToFirst()){
			cursor.moveToFirst();
			deporte.setID(Integer.parseInt(cursor.getString(0)));
			deporte.setDeporte(cursor.getString(1));
			String datestr = null;
		    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		    
		    try {
		    	final Date dt = (Date) dateFormat.parse(cursor.getString(2));
		    	datestr = dateFormat.format(dt);
		    } catch (Exception e) {
		        // TODO Auto-generated catch block
		        e.printStackTrace();
		    }
		    deporte.setFecha(datestr);
			
			deporte.setCalorias(Double.parseDouble(cursor.getString(3)));
			deporte.setTiempo(Double.parseDouble(cursor.getString(2)));
			cursor.close();
		}
		else{
			deporte=null;
		}
		
		return deporte;
	}

}
/*
 * public class PeliculaOperations {
	private SQLiteDatabase db;
	private PeliculaHelper dbHelper;
	public static final String TABLE_PELICULAS="peliculas";
	public static final String COLUMN_ID="_id";
	public static final String COLUMN_NAME="name";
	public static final String COLUMN_RANKING="ranking";
	
		public PeliculaOperations(Context context){
			dbHelper=new PeliculaHelper(context);
		}
		public void open()throws SQLException{
			db=dbHelper.getWritableDatabase();
		}
		public void close(){
			db.close();
			
		}
		public void addPelicula(Pelicula pelicula){
			ContentValues values=new ContentValues();
			values.put(COLUMN_NAME, pelicula.getName());
			values.put(COLUMN_RANKING, pelicula.getRanking());
			db.insert(TABLE_PELICULAS, null, values);
			
		}
		public Pelicula findPelicula(String nombrePelicula){
			String query= "Select * FROM "+TABLE_PELICULAS+" WHERE "+COLUMN_NAME+" = \""+nombrePelicula+"\"" ;
			Cursor cursor=db.rawQuery(query, null);
			Pelicula pelicula=new Pelicula();
			if (cursor.moveToFirst()){
				cursor.moveToFirst();
				pelicula.setID(Integer.parseInt(cursor.getString(0)));
				pelicula.setName(cursor.getString(1));
				pelicula.setRanking(Integer.parseInt(cursor.getString(2)));
				cursor.close();
			}
			else{
				pelicula=null;
			}
			
			return pelicula;
		}
		public boolean deletePelicula(String nombrePelicula){
			boolean result=false;
			String query="Select * FROM " + TABLE_PELICULAS + " WHERE "+ COLUMN_NAME+ " = \""+nombrePelicula+ "\"";
			Cursor cursor=db.rawQuery(query, null);
			Pelicula pelicula=new Pelicula();
			if (cursor.moveToFirst()){
				pelicula.setID(Integer.parseInt(cursor.getString(0)));
				db.delete(TABLE_PELICULAS, COLUMN_ID+" = ?", new String[]{String.valueOf(pelicula.getID())});
				cursor.close();
				result=true;
			}
			return result;
		}

	
}
 * 
 * 
 * */