package activities;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;

import com.example.myfitnessapplication.Deporte;
import com.example.myfitnessapplication.DeporteOperations;
import com.example.myfitnessapplication.Preferences;
import com.example.myfitnessapplication.R;

public class Cronometro extends Activity {
	Chronometer focus;
	Button iniciar, detener, reiniciar, guardar, playlist;
	long timeWhenStopped = 0;
	DeporteOperations dao;
	long tiempo, segundos, minutos, horas;
	String deporteselec;
	double calorias;
	int peso, altura;

	Calendar c = Calendar.getInstance();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cronometro);
		dao = new DeporteOperations(this);
		dao.open();
		focus = (Chronometer) findViewById(R.id.chronometer_final);
		iniciar = (Button) findViewById(R.id.iniciar_bot);
		detener = (Button) findViewById(R.id.stop_bot);
		reiniciar = (Button) findViewById(R.id.reiniciar_bot);
		guardar = (Button) findViewById(R.id.guardar_bot);
		playlist = (Button) findViewById(R.id.playlist_bot);
		// LISTO PARA QUE USEN LOS DATOS EN EL CALCULO:
		//
		peso = Preferences.getPeso(this);
		altura = Preferences.getAltura(this);
		// LOS VALORES DEFAULT SE PUEDEN MODIFICAR EN LA CLASE Prefereces.java
		//

		Bundle datos = getIntent().getExtras();
		if (datos != null) {
			deporteselec = datos.getString("deporte");
		}

		iniciar.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				focus.setBase(SystemClock.elapsedRealtime() + timeWhenStopped);
				focus.start();
			}
		});

		detener.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				timeWhenStopped = focus.getBase()
						- SystemClock.elapsedRealtime();
				focus.stop();
				tiempo = SystemClock.elapsedRealtime() - focus.getBase();
				horas = tiempo / 3600000;
				minutos = (tiempo % 3600000) / 60000;
				segundos = ((tiempo % 3600000) % 60000) / 1000;
			}
		});
		reiniciar.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				timeWhenStopped = 0;
				focus.setBase(SystemClock.elapsedRealtime());

			}
		});
		guardar.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				timeWhenStopped = SystemClock.elapsedRealtime()
						- focus.getBase(); 				;
				focus.stop();
				// tiempo = (double) timeWhenStopped;
				tiempo = SystemClock.elapsedRealtime() - focus.getBase();
				horas = tiempo / 3600000;
				minutos = (tiempo % 3600000) / 60000;
				segundos = ((tiempo % 3600000) % 60000) / 1000;
				if (deporteselec.equals("Correr")) {
					calorias = (peso * 2.2) * tiempo * .142;

				} else if (deporteselec.equals("Spinning")) {
					calorias = (peso * 2.2) * tiempo * .053;
				} else if (deporteselec.equals("Caminar")) {
					calorias = (peso * 2.2) * tiempo * .062;

				} else if (deporteselec.equals("Baloncesto")) {
					calorias = (peso * 2.2) * tiempo * .045;

				} else if (deporteselec.equals("Futbol")) {
					calorias = (peso * 2.2) * tiempo * .061;

				} else if (deporteselec.equals("Natacion")) {
					calorias = (peso * 2.2) * tiempo * .142;

				} else if (deporteselec.equals("Atletismo")) {
					calorias = (peso * 2.2) * tiempo * .142;

				}
				// newDeporte(v);

				Intent guarda = new Intent(Cronometro.this, Resultado.class);
				guarda.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				guarda.putExtra("segundos", segundos);
				guarda.putExtra("minutos", minutos);
				guarda.putExtra("horas", horas);
				// guarda.putExtra("tiempo", timeWhenStopped);
				guarda.putExtra("deporte", deporteselec);
				guarda.putExtra("calorias", calorias);
				startActivity(guarda);
				Cronometro.this.finish();

				
				
				/*
				 * Intent dep=new Intent(Medicion.this, Cronometro.class);
				 * startActivity(dep);
				 */
			}
		});

		playlist.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent play = new Intent(Cronometro.this, Playlist.class);
				play.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

				startActivity(play);

			}
		});

	}

	public void newDeporte(View view) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		String date = sdf.format(new Date());

		Deporte deporte = new Deporte(deporteselec, date, calorias, tiempo);
		dao.addDeporte(deporte);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.conf:
			Intent confi = new Intent(Cronometro.this, Configuracion.class);
			startActivity(confi);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
