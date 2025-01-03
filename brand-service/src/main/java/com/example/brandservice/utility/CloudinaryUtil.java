package com.example.brandservice.utility;

public class CloudinaryUtil {
    public static String extractPublicIdFromUrl(String url) {
		if (url == null || url.isEmpty()) {
			return null;
		}

		// Split the URL by '/'
		String[] parts = url.split("/");

		// The `public_id` is the last segment in the URL path
		String publicId = parts[parts.length - 1];

		return publicId;
	}
}
