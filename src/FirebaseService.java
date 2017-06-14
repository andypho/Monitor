
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseCredentials;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirebaseService {

	public FirebaseService() {
		FileInputStream serviceAccount;
		FirebaseOptions options;
		
		// Initialize the app with a service account, granting admin privileges
		try {
			serviceAccount = new FileInputStream("./homemonitor-c0594-firebase-adminsdk-swf9x-b789a9b358.json");
			
			options = new FirebaseOptions.Builder()
					.setCredential(FirebaseCredentials.fromCertificate(serviceAccount))
					.setDatabaseUrl("https://homemonitor-c0594.firebaseio.com").build();
			
			FirebaseApp.initializeApp(options);
			
			// As an admin, the app has access to read and write all data, regardless of Security Rules
			DatabaseReference ref = FirebaseDatabase
			    .getInstance()
			    .getReference("restricted_access/secret_document");
			ref.addListenerForSingleValueEvent(new ValueEventListener() {
			    @Override
			    public void onDataChange(DataSnapshot dataSnapshot) {
			    	System.out.println("onDatachanged");
			    	
			    	Device device = dataSnapshot.getValue(Device.class);
			    	System.out.println(device.getIpAddress());
			    	
//			    	Object document = dataSnapshot.getValue();
			    	
//			        System.out.println(document);
			    }

				@Override
				public void onCancelled(DatabaseError arg0) {
					// TODO Auto-generated method stub
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("Firebase connected");
	}
	
	public void writeToDatabase(Map<String, Device> users){
		final FirebaseDatabase database = FirebaseDatabase.getInstance();
		DatabaseReference ref = database.getReference("server");
		DatabaseReference usersRef = ref.child("users");
		
		usersRef.setValue(users);
//		usersRef.child(users.getMacAddress()).setValue(users);
//		usersRef.push().setValue(device);
	}
	
	public void updateDatabase(){
		
	}
}
