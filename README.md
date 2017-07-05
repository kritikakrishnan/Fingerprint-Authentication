# Fingerprint-Authentication

The applcation has a sign up and login activity and a welcome activity. 
The Sign Up page gets information about the user - first name, last name, username, email address, password and phone number.
The Login page allows login using email address and password or login using fingerprint.
The email address password login authntication is handled by firebase a no sql database and the user details are stored in the firebase database.
The fingerprint authentication takes multiple finger prints already registered in the phone through the emulator's settings. 
Fingerprint can be registered in the emulatr by first adding a screen lock and then adding fingerprints through the db command ./adb -e emu finger touch <finger print ID>
Multiple users can be created using android's inbuilt fingerprint scanner, because android does not reveal the fingerprnt image and without the image there is nothing to compare with.
However, an external fingerprint scanner can be used for this purpose.
