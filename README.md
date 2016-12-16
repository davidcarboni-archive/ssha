# Liferay SSHA migration helper

## What is it?

This API provides some limited functionality for working with Liferay SSHA password hashes.

## Why is it here?

SSHA is a poor choice of password hash these days, so this API can help you move away from using it.

## Tell me more

If you have passwords hashed with the SSHA option in Liferay, an interim approach you can take is to
feed those hashes into a stronger hashing algorithm and replace the "double-hashed" records when users
log in.

This means you need something to compute known SSHA Liferay hashes when a user enters their password.
That allows you to verify the password is correct before updating to a better hash (pbkdf2, bcrypt or scrypt).

It goes something a little like this:

 * Take a legacy Liferay SSHA hash.
 * Extract the salt value and store it.
 * Feed the SSHA hash into, say, bcrypt.
 * You now have a known salt (to reproduce the SSHA hash) and a bcrypt hash value computed using the SSHA hash as the "password".
 * When a user logs in with their plaintext password, take the saved salt and compute the Liferay SSHA hash.
 * Use the computed hash as the input to password verification.
 * If verification succeeds, compute a new bcrypt hash, using the plaintext password you just used to compute the SSHA hash.
 * Store the new bcrypt hash and discard the previously saved salt value.
 * The next time the user logs in, you see that there's no legacy salt and use bcrypt directly with the plaintext password.
 * Your blood pressure and sense of panic should now decrease.

## How does it work?

This API contains a copy of the Liferay SSHA hashing code, gently eased out into isolation.

When you get the original Liferay SSHA hash, POST it to '/salt' to extract the salt value. It will come back as Base-64. Store this.

When you need to compute a Liferay SSHA hash from the stored salt and a plaintext password, POST these to '/hash'. You'll get back the original SSHA hash.

Now go ahead and use that original hash as input to bcrypt (see above).

## Notes

Don't use SSHA. It's relatively trivial to brute-force.

Use scrypt if you can, otherwise bcrypt or pbkdf2. All of these are way slower and/or require more compute resources to crack, reducing the cost-effectiveness to an attacker of doing so.

This may not help you migrate passwords if you're sticking with Liferay. There's probably a way to do it, but I've written this to help move authentication out to a microservice.

---

David