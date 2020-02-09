package fr.isen.bilisari.androidtoolbox.service

import android.util.Base64
import java.nio.charset.StandardCharsets
import java.security.*
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import javax.crypto.Cipher

class Encryption {

    companion object {
        private fun PublicKey.key() = Base64.encodeToString(this.encoded, Base64.DEFAULT)
        private fun PrivateKey.key() = Base64.encodeToString(this.encoded, Base64.DEFAULT)


        // Generate a pair public/private keys
        fun generateKeyPair(): Pair<String, String> {
            val kpair: KeyPair
            val kgen: KeyPairGenerator = KeyPairGenerator.getInstance(CRYPTO_METHOD)

            kgen.initialize(CRYPTO_BITS)
            kpair = kgen.genKeyPair()

            return Pair(kpair.public.key(), kpair.private.key())
        }

        // Converts a String to a PublicKey object
        private fun String.toPublicKey(): PublicKey {
            return KeyFactory.getInstance(CRYPTO_METHOD)
                .generatePublic(X509EncodedKeySpec(Base64.decode(this, Base64.DEFAULT)))
        }

        // Converts a String to a PrivateKey object
        private fun String.toPrivateKey(): PrivateKey {
            return KeyFactory.getInstance(CRYPTO_METHOD)
                .generatePrivate(PKCS8EncodedKeySpec(Base64.decode(this, Base64.DEFAULT)))
        }


        // Encrypt data with given key
        fun encrypt(data: String, key: String): String {
            val cipher = Cipher.getInstance(CRYPTO_TRANFORM)
            cipher.init(Cipher.ENCRYPT_MODE, key.toPublicKey())

            val encrypted = cipher.doFinal(data.toByteArray(StandardCharsets.UTF_8))

            return Base64.encodeToString(encrypted, Base64.DEFAULT)
        }

        // Decrypt data with given key
        fun decrypt(data: String, key: String): String {
            val cipher = Cipher.getInstance(CRYPTO_TRANFORM)
            cipher.init(Cipher.DECRYPT_MODE, key.toPrivateKey())

            val decrypted = cipher.doFinal(Base64.decode(data, Base64.DEFAULT))

            return String(decrypted, StandardCharsets.UTF_8)
        }


        private const val CRYPTO_METHOD = "RSA"
        private const val CRYPTO_BITS = 2048
        private const val CRYPTO_TRANFORM = "RSA/ECB/OAEPWithSHA1AndMGF1Padding"
    }
}
    /*
    // Generate a pair public/private keys
    fun generateKeyPair(prefs: SharedPreferences) {
        val kpair: KeyPair
        val kgen: KeyPairGenerator = KeyPairGenerator.getInstance(CRYPTO_METHOD)

        kgen.initialize(CRYPTO_BITS)
        kpair = kgen.genKeyPair()

        prefs.edit()
            .putString(PUBLIC_KEY, kpair.public.key())
            .putString(PRIVATE_KEY, kpair.private.key())
            .apply()
    }

    // Converts a String to a PublicKey object
    fun String.toPublicKey() : PublicKey {
        return KeyFactory.getInstance(CRYPTO_METHOD).generatePublic(X509EncodedKeySpec(Base64.decode(this, Base64.DEFAULT)))
    }

    // Converts a String to a PrivateKey object
    fun String.toPrivateKey() : PrivateKey {
        return KeyFactory.getInstance(CRYPTO_METHOD).generatePrivate(PKCS8EncodedKeySpec(Base64.decode(this, Base64.DEFAULT)))
    }


    // Encrypt data with given key
    fun encrypt(data: String, key: String) : String {
        val cipher = Cipher.getInstance(CRYPTO_TRANFORM)
        cipher.init(Cipher.ENCRYPT_MODE, key.toPublicKey())

        val encrypted = cipher.doFinal(data.toByteArray(StandardCharsets.UTF_8))

        return Base64.encodeToString(encrypted, Base64.DEFAULT)
    }

    // Decrypt data thanks to key saved in SharedPreferences
    fun decrypt(data: String, prefs: SharedPreferences) : String? {
        val key = prefs.getString(PRIVATE_KEY, "")

        key?.let {
            val cipher = Cipher.getInstance(CRYPTO_TRANFORM)
            cipher.init(Cipher.DECRYPT_MODE, key.toPrivateKey())

            val decrypted = cipher.doFinal(Base64.decode(data, Base64.DEFAULT))

            return String(decrypted, StandardCharsets.UTF_8)
        }

        return null
    }


    companion object {
        private const val CRYPTO_METHOD = "RSA"
        private const val CRYPTO_BITS = 2048
        private const val CRYPTO_TRANFORM = "RSA/ECB/OAEPWithSHA1AndMGF1Padding"

        private const val PUBLIC_KEY = "public_key"
        private const val PRIVATE_KEY = "private_key"
    }
}*/