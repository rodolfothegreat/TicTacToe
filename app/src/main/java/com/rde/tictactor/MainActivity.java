package com.rde.tictactor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView[][] cells = new TextView[3][3];

    private boolean isCross = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_table);

        cells[0][0] = findViewById(R.id.tv00);
        cells[0][1] = findViewById(R.id.tv01);
        cells[0][2] = findViewById(R.id.tv02);

        cells[1][0] = findViewById(R.id.tv10);
        cells[1][1] = findViewById(R.id.tv11);
        cells[1][2] = findViewById(R.id.tv12);

        cells[2][0] = findViewById(R.id.tv20);
        cells[2][1] = findViewById(R.id.tv21);
        cells[2][2] = findViewById(R.id.tv22);

        for (TextView[] arow : cells )
        {
            for(TextView cell : arow)
            {
                cell.setOnClickListener(this);
                cell.setTextColor(getResources().getColor(android.R.color.background_dark ));
                cell.setText("");
            }
        }

    }

    @Override

    protected void onResume()
    {
        super.onResume();
        getWinner(true);
        getWinner(false);
    }

    private boolean getWinner(boolean cross)
    {
        Resources res = getResources();
        String piece = res.getString(R.string.nought);
        if(cross)
            piece = res.getString(R.string.cross);

        Map<Integer,Integer> mapi=new HashMap<Integer,Integer>();
        Map<Integer,Integer> mapj=new HashMap<Integer,Integer>();
        for(int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                if (!cells[i][j].getText().toString().equals(piece))
                    continue;
                int avalue = 0;
                if (mapi.containsKey(i)) {
                    avalue = mapi.get(i);
                }
                avalue++;
                mapi.put(i, avalue);
                if (avalue == cells[i].length) {
                    for(int k = 0; k < cells[i].length; k++)
                    {
                        cells[i][ k].setTextColor(res.getColor( R.color.colorWinner));
                    }
                    return true;
                }

                avalue = 0;
                if(mapj.containsKey(j))
                {
                    avalue = mapj.get(j);
                }
                avalue++;
                mapj.put(j, avalue);
                if(avalue == cells[i].length)
                {
                    for(int k = 0; k < cells[i].length; k++)
                    {
                        cells[k][ j].setTextColor(res.getColor( R.color.colorWinner));
                    }
                    return true;

                }

            }
        }
        boolean diag1 = true;
        boolean diag2 = true;
        for(int i = 0; i < cells.length; i++)
        {
            if(!cells[i][i].getText().toString().equals(piece))
            {
                diag1 = false;
            }

            if(!cells[cells.length - i - 1][i].getText().toString().equals(piece))
            {
                diag2 = false;
            }

        }
        if(diag1)
        {
            for (int i = 0; i < cells.length; i++)
            {
                cells[i][ i].setTextColor(res.getColor( R.color.colorWinner));
            }
            return true;
        }

        if(diag2)
        {
            for (int i = 0; i < cells.length; i++)
            {
                cells[cells.length - i - 1][i].setTextColor(res.getColor( R.color.colorWinner));
            }
            return true;

        }


        return false;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void cleanBoard()
    {
        for (TextView[] arow : cells )
        {
            for(TextView cell : arow)
            {
                cell.setText("");
                cell.setTextColor(getResources().getColor(android.R.color.background_dark ));
            }
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.new_game) {
            cleanBoard();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {
        if (getWinner(true) || getWinner(false))
            return;
        TextView acell = (TextView) v;
        String acontents = acell.getText().toString();
        if(acontents.equals(""))
        {
            acell.setText(isCross? R.string.cross : R.string.nought );
            isCross = !isCross;
        }
        if (getWinner(true) || getWinner(false))
            return;

    }
}
