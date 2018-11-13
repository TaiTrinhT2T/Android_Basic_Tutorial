package example.dell.androiddashboarddesign;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Dashboard News feed button
        Button btn_newsfeed = (Button) findViewById(R.id.btn_news_feed);

        // Dashboard Friends button
        Button btn_friends = (Button) findViewById(R.id.btn_friends);

        // Dashboard Messages button
        Button btn_messages = (Button) findViewById(R.id.btn_messages);

        // Dashboard Places button
        Button btn_places = (Button) findViewById(R.id.btn_places);

        // Dashboard Events button
        Button btn_events = (Button) findViewById(R.id.btn_events);

        // Dashboard Photos button
        Button btn_photos = (Button) findViewById(R.id.btn_photos);

        /**
         * Handling all button click events
         * */

        // Listening to News Feed button click
        btn_newsfeed.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Launching News Feed Screen
                Intent i = new Intent(getApplicationContext(), NewsFeedActivity.class);
                startActivity(i);
            }
        });

        // Listening Friends button click
        btn_friends.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Launching News Feed Screen
                Intent i = new Intent(getApplicationContext(), FriendsActivity.class);
                startActivity(i);
            }
        });

        // Listening Messages button click
        btn_messages.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Launching News Feed Screen
                Intent i = new Intent(getApplicationContext(), MessagesActivity.class);
                startActivity(i);
            }
        });

        // Listening to Places button click
        btn_places.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Launching News Feed Screen
                Intent i = new Intent(getApplicationContext(), PlacesActivity.class);
                startActivity(i);
            }
        });

        // Listening to Events button click
        btn_events.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Launching News Feed Screen
                Intent i = new Intent(getApplicationContext(), EventsActivity.class);
                startActivity(i);
            }
        });

        // Listening to Photos button click
        btn_photos.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Launching News Feed Screen
                Intent i = new Intent(getApplicationContext(), PhotosActivity.class);
                startActivity(i);
            }
        });
    }
}
