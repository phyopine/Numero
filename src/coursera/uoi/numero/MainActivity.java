package coursera.uoi.numero;

import java.text.DateFormatSymbols;
import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	private static final String TAG = "Numero";
	private TextView tvDisplayText;
	private TextView tvLifePathText;
	private Button btnChangeDate;
 
	private int year;
	private int month;
	private int day;
 
	static final int DATE_DIALOG_ID = 999;
 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
 
		tvDisplayText = (TextView) findViewById(R.id.tvDisplayText);
		tvLifePathText = (TextView) findViewById(R.id.tvLifePathText);
		
		setCurrentDateOnView();
		addListenerOnButton();
 
	}
 
	// display current date
	public void setCurrentDateOnView() {

		final Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);
 

 
	}
 
	public void addListenerOnButton() {
 
		btnChangeDate = (Button) findViewById(R.id.btnEnterBd);
 
		btnChangeDate.setOnClickListener(new OnClickListener() {
 
			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View v) {
 
				showDialog(DATE_DIALOG_ID);
 
			}
 
		});
 
	}
 
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_DIALOG_ID:
		   // set date picker as current date
		   return new DatePickerDialog(this, datePickerListener, 
                         year, month,day);
		}
		return null;
	}
 
	private DatePickerDialog.OnDateSetListener datePickerListener 
                = new DatePickerDialog.OnDateSetListener() {
 
		// when dialog box is closed, below method will be called.
		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int selectedDay) {
			year = selectedYear;
			// Month is 0 based
			month = selectedMonth;
			day = selectedDay;
			
			int realMonth = month+1;
			int lifePathNumber =  getLifePathNumber(year,realMonth,day);
			
			String lifePathNumberString = Integer.toString(lifePathNumber);
			String monthName = getMonthForInt(month);
			String selectedDate = monthName + '-' + Integer.toString(day) + '-' + Integer.toString(year);
			String displayText = "Birthday you entered is " + selectedDate + " and your life path number is " + lifePathNumberString;
			
			//String lifePathString = "lp_" + lifePathNumberString + "_text";
			String prefix = getString(R.string.life_path_prefix);
			String lifePathString = getString(getResources().getIdentifier(prefix+lifePathNumberString,"string", getPackageName()));
			Log.d(TAG,lifePathString);
			
			tvDisplayText.setText(displayText);
			tvLifePathText.setText(Html.fromHtml(lifePathString));
 
 
		}
	};
	
	private int getLifePathNumber(int cYear, int cMonth, int cDay) {
		int lifeNum = 0;
		
		lifeNum = sumDigit(sumDigit(cYear) + sumDigit(cMonth) + sumDigit(cDay));
		
		return lifeNum;
	}
	
	//Taking the modulo 9 of any number will return the sum of digits of that number until a single digit number is obtained.
	//If the number is a multiple of 9, then the sum will will be 9
	private int sumDigit(int n){

	      int sum = n % 9;
	      if(sum == 0){
	          if(n > 0)
	               return 9;
	      }
	      return sum;
	}
	private String getMonthForInt(int num) {
        String month = "wrong";
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();
        if (num >= 0 && num <= 11 ) {
            month = months[num];
        }
        return month;
    }

}
