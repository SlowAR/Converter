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
	
	private boolean dotPressed;
	private int posNum;
	private int posOp;
	private int lineLength;
	private BigDecimal temp;
	private BigDecimal res;	
	private boolean resEmpty;
	private BigDecimal zpz;
	private String getValues;
	private boolean inf;
	private boolean operPressed;
	private boolean nonfull;
	private boolean negadded;
	private boolean negative;
	private boolean usedValues;
	
	private ArrayList<BigDecimal> numbers = new ArrayList<BigDecimal>();
	private ArrayList<String> operations = new ArrayList<String>();
	
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
        
        temp = new BigDecimal(0);
        zpz = new BigDecimal(1);
        operPressed = true;
        resEmpty = true;
        inf = false;
        nonfull = false;
        negadded = false;
        negative = false;
        usedValues = false;
        
        return view;
    }

	@Override
	public void onClick(View v)
	{
		switch(v.getId())
		{
		case R.id.zero:
			number(new BigDecimal(0));
			break;
		case R.id.one:
			number(new BigDecimal(1));
			break;
		case R.id.two:
			number(new BigDecimal(2));
			break;
		case R.id.three:
			number(new BigDecimal(3));
			break;
		case R.id.four:
			number(new BigDecimal(4));
			break;
		case R.id.five:
			number(new BigDecimal(5));
			break;
		case R.id.six:
			number(new BigDecimal(6));
			break;
		case R.id.seven:
			number(new BigDecimal(7));
			break;
		case R.id.eight:
			number(new BigDecimal(8));
			break;
		case R.id.nine:
			number(new BigDecimal(9));
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
		case R.id.perbtn:
			operation("%");
			break;
		case R.id.ac:
			ac();
			break;
		case R.id.dot:
			if(!dotPressed)
			{
				numWind.append(".");
				dotPressed = true;
			}
			break;
		case R.id.useValues:
			useValues();
			break;
		case R.id.pmbtn:
			delSign();
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
		temp = new BigDecimal(0);
		resEmpty = true;
		dotPressed = false;
		inf = false;
		pmbtn.setEnabled(true);
		zpz = new BigDecimal(1);
		operPressed = true;
		nonfull = false;
		negadded = false;
		negative = false;
		usedValues = false;
	}
	
	private void number(BigDecimal number)
	{
		if(!numbers.isEmpty() && numbers.get(numbers.size()-1).scale() > 0 && nonfull)
		{
			dotPressed = true;
		}
		if(inf)
			ac();
		if(nonfull)	//changing in collection
			
		{
			if(dotPressed)
			{
				if(numbers.get(numbers.size()-1).compareTo(new BigDecimal(0)) == -1)
				{
					numWind.append(number.toString());
					for(int i = 0; i <= numbers.get(numbers.size()-1).scale(); i++)
						number = number.divide(new BigDecimal(10), 9, RoundingMode.UP);
					numbers.set(numbers.size()-1, numbers.get(numbers.size()-1).subtract(number));
				}
				else
				{
					numWind.append(number.toString());
					for(int i = 0; i <= numbers.get(numbers.size()-1).scale(); i++)
						number = number.divide(new BigDecimal(10), 9, RoundingMode.UP);
					numbers.set(numbers.size()-1, numbers.get(numbers.size()-1).add(number));
				}
			}
			else
			{
				numWind.append("" + number);
				numbers.set(numbers.size()-1, numbers.get(numbers.size()-1).multiply(new BigDecimal(10)).add(number));
			}
		}
		else	//changing in temp
		{
			numWind.append("" + number);
			temp = temp.multiply(new BigDecimal(10)).add(number);
			if(dotPressed)
				zpz = zpz.multiply(new BigDecimal(10));
		}
		operPressed = false;
		negadded = false;
	}
	
	private void operation(String operation)
	{
		if(inf)
			ac();
		
		if(numbers.size() == operations.size() && temp.compareTo(new BigDecimal(0)) == 0)
			operPressed = true;
		
		if(usedValues)
		{
			operPressed = false;
			usedValues = false;
		}
		
		if(!operPressed)
		{
			operations.add(operation);
			numWind.append(operation);
			operPressed = true;
		}
		else
		{
			if(numbers.isEmpty() && operation.equals("-"))
			{
				negative = true;
				numWind.append("-");
				return;
			}
			else if(numbers.isEmpty() && operation.equals("+"))
			{
				negative = false;
				numWind.setText("");
				return;
			}
			
			if(operations.isEmpty())
				return;
			
			if(operations.get(operations.size()-1).equals("*") || operations.get(operations.size()-1).equals("/"))
			{
				if(operation.equals("+"))
				{
					if(negadded)
						delLastSign();
					else
						negadded = true;
					
					numWind.append("+");
					negative = false;
					return;
				}
				else if(operation.equals("-"))
				{
					if(negadded)
						delLastSign();
					else
						negadded = true;
					
					numWind.append("-");
					negative = true;
					return;
				}
				else
				{
					if(negadded && operation.equals("*"))
					{
						delLastSign();
						delLastSign();
						operations.remove(operations.size()-1);
						operations.add(operation);
						numWind.append(operation);
						negadded = false;
						negative = false;
						return;
					}
					else if(negadded && operation.equals("/"))
					{
						delLastSign();
						delLastSign();
						operations.remove(operations.size()-1);
						operations.add(operation);
						numWind.append(operation);
						negadded = false;
						negative = false;
						return;
					}
					else
					{
						delLastSign();
						operations.remove(operations.size()-1);
						operations.add(operation);
						numWind.append(operation);
						return;
					}
				}
			}
			else
			{
				delLastSign();
				operations.remove(operations.size()-1);
				operations.add(operation);
				numWind.append(operation);
				return;
			}
		}
		
		if(dotPressed && !nonfull)
		{
			if(resEmpty)
			{
				numbers.add(temp.divide(zpz));
				zpz = new BigDecimal(1);
				dotPressed = false;
			}
		}
		else if (!dotPressed && !nonfull)
		{
			if(resEmpty)
			{
				numbers.add(temp);
			}
		}
		else if(dotPressed && nonfull)
		{
			numbers.set(numbers.size()-1, numbers.get(numbers.size()-1).divide(zpz).stripTrailingZeros());
			zpz = new BigDecimal(1);
			dotPressed = false;
			nonfull = false;
		}
		else if(!dotPressed && nonfull)
		{
			nonfull = false;
		}
		
		if(negative)
		{
			numbers.set(numbers.size()-1, numbers.get(numbers.size()-1).negate());
			negative = false;
		}
		else if(!negative && numbers.size() > 1)
			numbers.set(numbers.size()-1, numbers.get(numbers.size()-1).abs());
		
		temp = new BigDecimal(0);
		resEmpty = true;
	}
	
	private void useValues()
	{
		if(inf)
			ac();
		try
		{
			getValues = new BigDecimal(ettwo.getText().toString()).toPlainString();
			if((numbers.size() == operations.size() && temp.compareTo(new BigDecimal(0)) != 0) || (numbers.size() > operations.size() && temp.compareTo(new BigDecimal(0)) == 0))
				Toast.makeText(getActivity(), R.string.nooper, Toast.LENGTH_LONG).show();
			else
			{
				numWind.append(getValues);
				numbers.add(new BigDecimal(getValues));
				resEmpty = false;
			}
			usedValues = true;
		}
		catch(Exception e)
		{
			Toast.makeText(getActivity(), R.string.noconvertedvalue, Toast.LENGTH_LONG).show();
		}
	}
	
	private void delSign()
	{
		if(inf)
		{
			numWind.setText("");
			ac();
			inf = false;
			return;
		}
		
		if(!numWind.getText().toString().equals(""))
			delLastSign();
		if(numWind.getText().toString().equals("") || numWind.getText().toString().equals("-"))
		{
			ac();
			return;
		}
		
		if(temp.compareTo(new BigDecimal(0)) != 0)
		{
			if(dotPressed && zpz.compareTo(new BigDecimal(1)) != 0)
				zpz = zpz.divide(new BigDecimal(10));
			if(dotPressed && zpz.compareTo(new BigDecimal(1)) == 0)
			{
				dotPressed = false;
				delLastSign();
			}
			temp = temp.divide(new BigDecimal(10));
			temp = new BigDecimal(temp.doubleValue()).setScale(0, RoundingMode.DOWN);
		}
		else
		{
			if(numbers.isEmpty())
			{
				ac();
				return;
			}
			
			if(numbers.size() > operations.size())
			{
				nonfull = false;
				int numScale = numbers.get(numbers.size()-1).scale();
				if(numScale > 1)
				{
					numbers.set(numbers.size()-1, numbers.get(numbers.size()-1).setScale(numScale-1, RoundingMode.DOWN));
					nonfull = true;
				}
				else if(numScale == 1)
				{
					numbers.set(numbers.size()-1, numbers.get(numbers.size()-1).setScale(0, RoundingMode.DOWN));
					delLastSign();
					if(numWind.getText().toString().equals("0"))
					{
						ac();
						return;
					}
					nonfull = true;
				}
				else
				{
					numbers.set(numbers.size()-1, numbers.get(numbers.size()-1).divide(new BigDecimal(10)).setScale(0, RoundingMode.DOWN).stripTrailingZeros());
					nonfull = true;
				}
					
				if(numbers.get(numbers.size()-1).compareTo(new BigDecimal(0)) == 0)
				{
					numbers.remove(numbers.size()-1);
					return;
				}
			}
			else if(numbers.size() == operations.size())
			{
				operations.remove(operations.size()-1);
				operPressed = false;
				nonfull = true;
				char lastChar = numWind.getText().toString().charAt(numWind.getText().length()-1);
				if(lastChar == '+' || lastChar == '-' || lastChar == '*' || lastChar == '/')
				{
					delLastSign();
					negative = false;
					negadded = false;
				}
			}
			else
				Toast.makeText(getActivity(), "Error with composing number and operation collections!", Toast.LENGTH_LONG).show();
		}
	}
	
	private void thenCalc()
	{
		if(numbers.isEmpty() && operations.isEmpty() && temp.compareTo(new BigDecimal(0)) == 0)
			return;
		
		if((operations.isEmpty() && !numbers.isEmpty()) || (operations.isEmpty() && temp.compareTo(new BigDecimal(0)) != 0))
			return;
		
		if(dotPressed)
			numbers.add(temp.divide(zpz));
		else
			numbers.add(temp);
		
		temp = new BigDecimal(0);
		if(negative)
		{
			numbers.set(numbers.size()-1, numbers.get(numbers.size()-1).negate());
			negadded = false;
		}
		
		if(dotPressed && nonfull)
		{
			numbers.set(numbers.size()-1, numbers.get(numbers.size()-1).divide(zpz).stripTrailingZeros());
			zpz = new BigDecimal(1);
			dotPressed = false;
			nonfull = false;
		}
		
		operPressed = false;
		nonfull = false;
		
		while(posOp <= operations.size()-1)
		{
			if(operations.get(posOp) == "*")
			{
				res = numbers.get(posNum).multiply(numbers.get(posNum+1));
				numbers.set(posNum, res.stripTrailingZeros());
				numbers.remove(posNum+1);
				operations.remove(posOp);
			}
			else if(operations.get(posOp) == "/")
			{
				try
				{
					res = numbers.get(posNum).divide(numbers.get(posNum+1), 10, RoundingMode.UP);
					res = res.stripTrailingZeros();
				}
				catch(ArithmeticException e)
				{
					if(numbers.get(posNum+1).compareTo(new BigDecimal(0)) == 0)
					{
						numWind.setText("");
						numWind.append("∞");
						inf = true;
						return;
					}
				}
				numbers.set(posNum, res.stripTrailingZeros());
				numbers.remove(posNum+1);
				operations.remove(posOp);
			}
			else if(operations.get(posOp) == "%")
			{
				res = numbers.get(posNum).divide(numbers.get(posNum+1), 9, RoundingMode.UP).multiply(new BigDecimal(100));
				res = res.stripTrailingZeros();
				numbers.set(posNum, res.stripTrailingZeros());
				numbers.remove(posNum+1);
				operations.remove(posOp);
			}
			else
			{
				posNum++;
				posOp++;
			}
		}								
		posNum = 0;
		posOp = 0;
		
		while(posOp <= operations.size()-1)
		{
			if(operations.get(posOp) == "+" && posOp == 0)
			{
				res = numbers.get(posNum);
				posNum++;
				res = res.add(numbers.get(posNum));
				posOp++;
			}
			else if(operations.get(posOp) == "+" && posOp != 0)
			{
				posNum++;
				res = res.add(numbers.get(posNum));
				posOp++;
			}
			else if(operations.get(posOp) == "-" && posOp == 0)
			{
				res = numbers.get(posNum);
				posNum++;
				res = res.subtract(numbers.get(posNum));
				posOp++;
			}
			else if(operations.get(posOp) == "-" && posOp != 0)
			{
				posNum++;
				res = res.subtract(numbers.get(posNum));
				posOp++;
			}
		}
		
		if(res.toBigInteger().toString().length() > 12 && res.compareTo(new BigDecimal(0)) == 1)
		{
			numWind.setText("");
			numWind.append("∞");
			inf = true;
		}
		else if(res.toBigInteger().toString().length() > 12 && res.compareTo(new BigDecimal(0)) == -1)
		{
			numWind.setText("");
			numWind.append("-∞");
			inf = true;
		}
		else if((res.compareTo(new BigDecimal("1.0E-9")) == -1 && res.compareTo(new BigDecimal(0)) == 1) || (res.compareTo(new BigDecimal(0)) == -1 && res.compareTo(new BigDecimal("-1.0E-9")) == 1))
		{
			res = new BigDecimal(0);
			numWind.setText("");
			numWind.append("0");
			numbers.set(numbers.size()-1, res);
		}
		else
		{
			numWind.setText("");
			numWind.append("" + res.stripTrailingZeros().toPlainString());
		}
		
		posOp = 0;
		posNum = 0;
		numbers.clear();
		numbers.add(res);
		operations.clear();
		resEmpty = false;
		zpz = new BigDecimal(1);
		nonfull = true;
	}
	
	private void delLastSign()
	{
		String line = numWind.getText().toString();
		lineLength = line.length();
		if (lineLength > 1)
		{
			String resLine = line.substring(0, line.length()-1);
			numWind.setText("");
			numWind.append(resLine);
		}
		else
			ac();
	}
}