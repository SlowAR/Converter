package by.slowar.converter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Calculator extends Fragment implements OnClickListener
{
	Button zero, one, two, three, four, five, six, seven, eight, nine;
	Button dot, div, mul, plus, minus, ac, useValues, pmbtn, perbtn, then;
	EditText numWind, ettwo;
	StringBuilder line = new StringBuilder();
	String resultLine;
	Main main;
	
	private boolean dotPressed;
	private int posNum;
	private int posOp;
	private int lineLength;
	private double temp;
	private double res;	
	private boolean resEmpty = true;
	private int zpz = 1;
	private String getValues;
	private int numZpz = 1;
	private boolean newNum = true;
	private boolean inf = false;
	
	ArrayList<Double> numbers = new ArrayList<Double>();
	ArrayList<String> operations = new ArrayList<String>();
	
	public Calculator(Main main)
	{
		this.main = main;
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
        View view = inflater.inflate(R.layout.calculator, container, false);
        zero = (Button) view.findViewById(R.id.zero);
        one = (Button) view.findViewById(R.id.one);
        two = (Button) view.findViewById(R.id.two);
        three = (Button) view.findViewById(R.id.three);
        four = (Button) view.findViewById(R.id.four);
        five = (Button) view.findViewById(R.id.five);
        six = (Button) view.findViewById(R.id.six);
        seven = (Button) view.findViewById(R.id.seven);
        eight = (Button) view.findViewById(R.id.eight);
        nine = (Button) view.findViewById(R.id.nine);
        dot = (Button) view.findViewById(R.id.dot);
        div = (Button) view.findViewById(R.id.div);
        mul = (Button) view.findViewById(R.id.mul);
        plus = (Button) view.findViewById(R.id.plus);
        minus = (Button) view.findViewById(R.id.minus);
        ac = (Button) view.findViewById(R.id.ac);
        useValues = (Button) view.findViewById(R.id.useValues);
        pmbtn = (Button) view.findViewById(R.id.pmbtn);
        perbtn = (Button) view.findViewById(R.id.perbtn);
        then = (Button) view.findViewById(R.id.then);
        numWind = (EditText) view.findViewById(R.id.numWind);
        ettwo = (EditText) getActivity().findViewById(R.id.ettwo);
        
        zero.setOnClickListener(this);
        one.setOnClickListener(this);
        two.setOnClickListener(this);
        three.setOnClickListener(this);
        four.setOnClickListener(this);
        five.setOnClickListener(this);
        six.setOnClickListener(this);
        seven.setOnClickListener(this);
        eight.setOnClickListener(this);
        nine.setOnClickListener(this);
        dot.setOnClickListener(this);
        div.setOnClickListener(this);
        mul.setOnClickListener(this);
        plus.setOnClickListener(this);
        minus.setOnClickListener(this);
        ac.setOnClickListener(this);
        useValues.setOnClickListener(this);
        pmbtn.setOnClickListener(this);
        perbtn.setOnClickListener(this);
        then.setOnClickListener(this);
        
        return view;
    }

	@Override
	public void onClick(View v)
	{
		switch(v.getId())
		{
		case R.id.zero:
			number(0);
			break;
		case R.id.one:
			number(1);
			break;
		case R.id.two:
			number(2);
			break;
		case R.id.three:
			number(3);
			break;
		case R.id.four:
			number(4);
			break;
		case R.id.five:
			number(5);
			break;
		case R.id.six:
			number(6);
			break;
		case R.id.seven:
			number(7);
			break;
		case R.id.eight:
			number(8);
			break;
		case R.id.nine:
			number(9);
			break;
		case R.id.dot:
			if(!dotPressed)
			{
				numWind.append(".");
				dotPressed = true;
			}
			break;
		case R.id.div:
			operation("/");
			break;
		case R.id.mul:
			operation("*");
			break;
		case R.id.plus:
			operation("+");
			break;
		case R.id.minus:
			operation("-");
			break;
		case R.id.ac:
			ac();
			break;
		case R.id.useValues:
			useValues();
			break;
		case R.id.pmbtn:
			delSign();
			break;
		case R.id.perbtn:
			percent();
			break;
		case R.id.then:
			thenCalc();
			break;
		}
	}
	
	private void ac()
	{
		numWind.setText("");
		numbers.clear();
		operations.clear();
		temp = 0;
		resEmpty = true;
		dotPressed = false;
		newNum = true;
		inf = false;
	}
	
	private void number(int number)
	{
		if(!resEmpty)
		{
			Toast.makeText(getActivity(), R.string.chooseOp, Toast.LENGTH_LONG).show();
		}
		else
		{
			if(number == 0)
			{
				numWind.append("0");
				temp = temp * 10;
			}
			else
			{
				numWind.append("" + number);
				if(temp == 0)
					temp = number;
				else
					temp = temp * 10 + number;
			}
			if(dotPressed)
				zpz = zpz * 10;
		}
	}
	
	private void operation(String operation)
	{
		if(resEmpty)
		{
			if(dotPressed)
			{
				if(temp != 0)
				{
					numbers.add(temp/zpz);
				}
				if(numbers.size() > operations.size())
				{
					operations.add(operation);
					numWind.append(operation);
				}
				temp = 0;
			}
			else
			{
				if(temp != 0)
					numbers.add(temp);
				if(numbers.size() > operations.size())
				{
					operations.add(operation);
					numWind.append(operation);
				}
				temp = 0;
			}
			zpz = 1;
			dotPressed = false;
		}
		else
		{
			numWind.append(operation);
			operations.add(operation);
			resEmpty = true;
			dotPressed = false;
		}
	}
	
	private void useValues()
	{
		try
		{
			getValues = ettwo.getText().toString();
			if((numbers.size() == operations.size() && temp != 0) || (numbers.size() > operations.size() && temp == 0))
			{
				Toast.makeText(getActivity(), R.string.nooper, Toast.LENGTH_LONG).show();
			}
			else
			{
				numWind.append(getValues);
				numbers.add(Double.parseDouble(getValues));
				resEmpty = false;
			}
		}
		catch(Exception e)
		{
			Toast.makeText(getActivity(), R.string.noconvertedvalue, Toast.LENGTH_LONG).show();
		}
	}
	
	private void percent()
	{
		if(resEmpty == true)
		{
			numWind.append("%");
			numbers.add(temp);
			operations.add("%");
			temp = 0;
		}
		else
		{
			numWind.append("%");
			operations.add("%");
			resEmpty = true;
		}
	}
	
	private void delSign()
	{
		boolean windClear = false;
		if(!inf)
		{
			if(!numWind.getText().toString().equals(""))
				delLastSign();
			if(temp != 0)
			{
				if(dotPressed && zpz != 1)
					zpz = zpz / 10;
				if(dotPressed && zpz == 1)
				{
					dotPressed = false;
					delLastSign();
				}
				temp = temp/10;
				temp = Math.floor(temp);
			}
			else if(temp == 0)
			{
				if(!numbers.isEmpty() || !operations.isEmpty())
				{
					if(numbers.size() > operations.size())
					{
						if(numbers.get(numbers.size()-1) - Math.floor(numbers.get(numbers.size()-1)) == 0)
						{
							double tempNum = numbers.get(numbers.size()-1)/10;
							numbers.set(numbers.size()-1, Math.floor(tempNum));
							if(numbers.get(numbers.size()-1) == 0)
							{
								numbers.remove(numbers.size()-1);
								windClear = delLastSign();
								if(!windClear)
									operations.remove(operations.size()-1);
								resEmpty = false;
							}
						}
						else
						{
							double getZpz = 0;
							if(newNum)
								newNum = false;
							else
								getZpz = new BigDecimal(numbers.get(numbers.size()-1)).setScale(numZpz, RoundingMode.UP).doubleValue();
							
							numZpz = 1;
							String numLine = String.valueOf(numbers.get(numbers.size()-1));
							int index = numLine.lastIndexOf(".");
							long floating = Long.parseLong(numLine.substring(index+1, numLine.length()));
							
							while(true)
							{
								floating = floating/10;
								if(floating != 0)
									numZpz++;
								else if(floating == 0)
									break;
							}
							numZpz--;
							getZpz = new BigDecimal(numbers.get(numbers.size()-1)).setScale(numZpz, RoundingMode.DOWN).doubleValue();
							numbers.set(numbers.size()-1, getZpz);
							if(numZpz == 0)
							{
								dotPressed = false;
								delLastSign();
								newNum = true;
							}
						}
					}
					else
					{
						operations.remove(operations.size()-1);
						resEmpty = false;
					}
				}
			}
		}
		else
		{
			numWind.setText("");
			ac();
			inf = false;
		}
	}
	
	private void thenCalc()
	{
		if(dotPressed)										//{ проверка на дробное число
		{
			numbers.add(temp/zpz);
		}
		else
			numbers.add(temp);								//проверка на дробное число }
		temp = 0;
		
		while(posOp <= operations.size()-1)					//{ обработка приоритетных операций (* / %)
		{
			if(operations.get(posOp) == "*")
			{
				res = numbers.get(posNum);
				res = res * numbers.get(posNum+1);
				numbers.set(posNum, res);
				numbers.remove(posNum+1);
				operations.remove(posOp);
			}
			else if(operations.get(posOp) == "/")
			{
				try
				{
					res = numbers.get(posNum);
					res = res / numbers.get(posNum+1);
					numbers.set(posNum, res);
					numbers.remove(posNum+1);
					operations.remove(posOp);
				}
				catch(ArithmeticException e)
				{
					ac();
					inf = true;
					numWind.setText("Inf");
				}
			}
			else if(operations.get(posOp) == "%")
			{
				res = numbers.get(posNum);
				res = (res / numbers.get(posNum+1)) * 100;
				numbers.set(posNum, res);
				numbers.remove(posNum+1);
				operations.remove(posOp);
			}
			else
			{
				posNum++;
				posOp++;
			}
		}													//обработка приоритетных операций (* / %) }									
		
		posNum = 0;
		posOp = 0;
		
		while(posOp <= operations.size()-1)					//{ обработка менее приоритетных операций (+ -)
		{
			if(operations.get(posOp) == "+" && posOp == 0)
			{
				res = 0;
				res = numbers.get(posNum);
				posNum++;
				res = res + numbers.get(posNum);
				posOp++;
			}
			else if(operations.get(posOp) == "+" && posOp != 0)
			{
				posNum++;
				res = res + numbers.get(posNum);
				posOp++;
			}
			
			else if(operations.get(posOp) == "-" && posOp == 0)
			{
				res = 0;
				res = numbers.get(posNum);
				posNum++;
				res = res - numbers.get(posNum);
				posOp++;
			}
			else if(operations.get(posOp) == "-" && posOp != 0)
			{
				posNum++;
				res = res - numbers.get(posNum);
				posOp++;
			}
		}													//обработка менее приоритетных операций (+ -) }
		
		if(operations.isEmpty() && !numbers.isEmpty())
		{
			res = numbers.get(posNum);
		}
		
		numWind.setText("");
		try
		{
			res = new BigDecimal(res).setScale(10, RoundingMode.UP).doubleValue();
		}
		catch(NumberFormatException e)
		{
			inf = true;
		}
		
		String strRes = ""+res;
		char[] chres = strRes.toCharArray();
		int n = 0;
		int reslen = strRes.length();
		boolean dot = false;
		for(int i = 0; i < reslen; i++)
		{
			if(!dot && chres[i] == '.')
			{
				dot = true;
			}
			else if(dot)
			{
				n++;
			}
		}
		if(n == 10)
		{
			res = new BigDecimal(res).setScale(9, RoundingMode.DOWN).doubleValue();
		}
		
		if(res - (int)res == 0)
			numWind.append("" + (int)res);
		else
			numWind.append("" + res);

		posOp = 0;
		posNum = 0;
		numbers.clear();
		numbers.add(res);
		operations.clear();
		res = 0;
		resEmpty = false;
		zpz = 1;
	}
	
	private boolean delLastSign()
	{
		String line = numWind.getText().toString();
		lineLength = line.length();
		if (lineLength > 1)
		{
			String resLine = line.substring(0, line.length()-1);
			numWind.setText("");
			numWind.append(resLine);
			return false;
		}
		else
		{
			ac();
			numbers.clear();
			operations.clear();
			return true;
		}
	}
}