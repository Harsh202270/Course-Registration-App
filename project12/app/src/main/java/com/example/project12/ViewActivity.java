package com.example.project12;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class ViewActivity extends AppCompatActivity {
    ListView lst1;
    ArrayList<String> titles = new ArrayList<>();
    ArrayList<String[]> rows = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        lst1 = findViewById(R.id.lst1);
        titles.add("ID | NAME | COURSE | FEE");

        try (SQLiteDatabase db = openOrCreateDatabase("SliteDb", Context.MODE_PRIVATE, null)) {
            Cursor c = db.rawQuery("SELECT * FROM records ", null);

            while (c.moveToNext()) {
                String id = c.getString(0);
                String name = c.getString(1);
                String course = c.getString(2);
                String fee = c.getString(3);

                rows.add(new String[]{id, name, course, fee});
                titles.add(id + " | " + name + " | " + course + " | " + fee);
            }

            lst1.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, titles));

            lst1.setOnItemClickListener((A, B, pos, i) -> {
                if (pos == 0) return; // skip header
                String[] row = rows.get(pos - 1);
                startActivity(new Intent(this, EditActivity.class)
                        .putExtra("id", row[0])
                        .putExtra("name", row[1])
                        .putExtra("course", row[2])
                        .putExtra("fee", row[3])
                );
            });
        }
        catch (Exception e) {
            Toast.makeText(this, "Data not found", Toast.LENGTH_SHORT).show();
        }
    }
}
