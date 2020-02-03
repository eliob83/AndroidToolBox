package fr.isen.bilisari.androidtoolbox.activity

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import fr.isen.bilisari.androidtoolbox.R
import fr.isen.bilisari.androidtoolbox.activity.adapter.ContactAdapter
import fr.isen.bilisari.androidtoolbox.model.Contact
import kotlinx.android.synthetic.main.activity_permissions.*


class PermissionsActivity : AppCompatActivity(), LocationListener {
    private var locationManager: LocationManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permissions)

        // Init contacts layout
        viewContacts.layoutManager = LinearLayoutManager(this)
        // Init Location manager
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        // Try to access Location and Contacts
        updateContacts()
        updateLocation()

        // Listeners
        imgCamera.setOnClickListener {
            showActionsDialog()
        }
    }

    override  fun onStop() {
        super.onStop()
        locationManager?.removeUpdates(this)
    }


    /*
    *   CAMERA / GALLERY
    */

    // Show an alert dialog asking for user choice
    private fun showActionsDialog() {
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle(resources.getText(R.string.permissions_dialog_title))

        val dialogItems = arrayOf(resources.getText(R.string.permissions_dialog_gallery), resources.getText(R.string.permissions_dialog_camera))
        dialog.setItems(dialogItems) { _, which ->
            when (which) {
                0 -> takeFromGallery()
                1 -> takeFromCamera()
            }
        }

        dialog.show()
    }

    // Open Camera app
    private fun takeFromCamera() {
        if (isPermissionGranted(this, PERMISSION_CAMERA)) {
            val intent1 = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

            if (intent1.resolveActivity(packageManager) != null) {
                startActivityForResult(intent1, IMAGE_CAMERA_CODE)
            }
        } else {
            askPermission(PERMISSION_CAMERA, PERMISSION_CODE_CAMERA) { showActionsDialog() }
        }
    }

    // Open Gallery selector
    private fun takeFromGallery() {
        if (isPermissionGranted(this, PERMISSION_STORAGE)) {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"

            startActivityForResult(intent, IMAGE_GALLERY_CODE)
        } else {
            askPermission(PERMISSION_STORAGE, PERMISSION_CODE_STORAGE) { showActionsDialog() }
        }
    }

    // Results of dialog actions
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                IMAGE_GALLERY_CODE ->
                    imgCamera.setImageURI(data?.data)
                IMAGE_CAMERA_CODE ->
                    imgCamera.setImageBitmap(data?.extras?.get("data") as Bitmap)
            }
        }
    }


    /*
    *   CONTACTS
    */

    // Set contacts adapter from phone contacts list
    private fun updateContacts() {
        viewContacts.adapter =
            ContactAdapter(
                getContacts(),
                this
            )
    }


    // Get contacts from phone
    private fun getContacts(): ArrayList<Contact> {
        val contacts = ArrayList<Contact>()

        if (isPermissionGranted(this, PERMISSION_CONTACTS)) {
            val resolver = contentResolver
            val cursor = resolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null)

            cursor?.let {
                // Browse cursor
                if (cursor.count > 0) {
                    while (cursor.moveToNext()) {
                        // Contact data
                        val id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
                        val name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                        val phoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)).toInt()

                        if (phoneNumber > 0) {
                            val cursorPhone = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?", arrayOf(id), null)

                            cursorPhone?.let {
                                // Each phone number is a new contact item
                                if (cursorPhone.count > 0) {
                                    while(cursorPhone.moveToNext()) {
                                        contacts.add(Contact(name, cursorPhone.getString(cursorPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))))
                                    }
                                }

                                cursorPhone.close()
                            }
                        }

                    }
                }

                cursor.close()
            }
        } else {
            askPermission(PERMISSION_CONTACTS, PERMISSION_CODE_CONTACTS) { updateContacts() }
        }

        return contacts
    }


    /*
    *   LOCATION
    */

    // Permission checked with custom function
    @SuppressLint("MissingPermission")
    // Request location update
    private fun updateLocation() {
        if (isPermissionGranted(this, PERMISSION_LOCATION)) {
            locationManager?.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1f, this)

            val location = locationManager?.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            if (location != null) {
                setLocation(location)
            }
        } else {
            askPermission(PERMISSION_LOCATION, PERMISSION_CODE_LOCATION) { updateLocation() }
        }
    }

    // Set location text
    private fun setLocation(location: Location) {
        textCoords.text = resources.getString(R.string.permissions_coords, location.latitude, location.longitude)
    }

    override fun onLocationChanged(location: Location) {
        textCoords.text = resources.getString(R.string.permissions_coords, location.latitude, location.longitude)
    }

    override fun onProviderDisabled(provider: String) {
        Toast.makeText(this, resources.getText(R.string.permissions_gps_disabled), Toast.LENGTH_SHORT).show()
        textCoords.text = resources.getText(R.string.permissions_gps_disabled)
        // Open settings panel
        //startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
    }

    override fun onProviderEnabled(provider: String) {
        Toast.makeText(this, resources.getText(R.string.permissions_gps_enabled), Toast.LENGTH_SHORT).show()
    }

    override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {

    }


    /*
    *   PERMISSIONS
    */

    // Ask given permission with related code, or invoke callback function if already granted
    private fun askPermission(permission: String, code: Int, callback: (() -> Unit)?) {
        if (isPermissionNotGranted(this, permission)) {
            ActivityCompat.requestPermissions(this, arrayOf(permission), code)
        } else {
            callback?.invoke()
        }
    }

    // Handle requested permission result
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            when (requestCode) {
                PERMISSION_CODE_LOCATION -> {
                    updateLocation()
                }
                PERMISSION_CODE_STORAGE, PERMISSION_CODE_CAMERA -> {
                    showActionsDialog()
                }
                PERMISSION_CODE_CONTACTS -> {
                    updateContacts()
                }
            }
        } else {
            Toast.makeText(this, resources.getText(R.string.permissions_denied), Toast.LENGTH_SHORT).show()
        }
    }


    companion object {
        fun isPermissionGranted(context: Context, permission: String) : Boolean {
            return (ActivityCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED)
        }

        fun isPermissionNotGranted(context: Context, permission: String) : Boolean {
            return !isPermissionGranted(context, permission)
        }

        // Image pick codes
        private const val IMAGE_GALLERY_CODE = 101
        private const val IMAGE_CAMERA_CODE = 102

        // Permissions code
        private const val PERMISSION_CODE_STORAGE = 11
        private const val PERMISSION_CODE_CONTACTS = 22
        private const val PERMISSION_CODE_LOCATION = 33
        private const val PERMISSION_CODE_CAMERA = 44

        // Permission values
        private const val PERMISSION_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE
        private const val PERMISSION_CONTACTS = Manifest.permission.READ_CONTACTS
        private const val PERMISSION_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION
        private const val PERMISSION_CAMERA = Manifest.permission.CAMERA
    }
}