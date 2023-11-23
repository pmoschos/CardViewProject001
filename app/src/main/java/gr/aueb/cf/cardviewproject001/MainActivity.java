package gr.aueb.cf.cardviewproject001;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.service.autofill.OnClickAction;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Movie> movieList;
    private MyAdapter myAdapter;
    private ConstraintLayout mainLayout;
    private String[] titles = {"The Shawshank Redemption", "The Godfather","Schindler's List","Inception ","Toy Story","The Dark Knight"};
    private String[] categories = {"Drama", "Crime/Drama","Historical Drama","Science Fiction/Thriller","Animation/Family","Action/Crime"};
    private int[] images = {
            R.drawable.image01,
            R.drawable.image02,
            R.drawable.image03,
            R.drawable.image04,
            R.drawable.image05,
            R.drawable.image06
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainLayout = findViewById(R.id.mainLayout);

        recyclerView = findViewById(R.id.recyclerView);
        movieList = new ArrayList<>();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // set my Data
        setMyDate();

        myAdapter = new MyAdapter(movieList);
        recyclerView.setAdapter(myAdapter);

        // ItemTouchHelper
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT
    ) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            int moviePosition = viewHolder.getAdapterPosition();
            Movie movie = movieList.remove(moviePosition);
            String movie_title = movie.getTitle();

            //movieList.remove(viewHolder.getAdapterPosition());
            // SOS !
            myAdapter.notifyDataSetChanged();


            // Snackbar.make(MainActivity.this, mainLayout, movie_title + " deleted.", Snackbar.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(MainActivity.this, mainLayout, movie_title + " deleted.", Snackbar.LENGTH_SHORT);
            snackbar.setDuration(8000)
                    .setTextColor(Color.BLACK)
                    .setBackgroundTint(Color.GRAY)
                    .setActionTextColor(Color.WHITE)
                    .setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    movieList.add(moviePosition, movie);
                    myAdapter.notifyDataSetChanged();
                }
            });

            // Customizing text size
            View snackbarView = snackbar.getView();
            TextView textView = snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18); // Example: 18sp
            textView.setTypeface(textView.getTypeface(), Typeface.BOLD); // Set text to bold
            //

            snackbar.show();

        }
    };

    private void setMyDate() {
        for(int i=0; i<titles.length; i++){
            Movie movie = new Movie(titles[i], categories[i], images[i]);
            movieList.add(movie);
        }
        for(int i=0; i<titles.length; i++){
            Movie movie = new Movie(titles[i], categories[i], images[i]);
            movieList.add(movie);
        }
    }
}
