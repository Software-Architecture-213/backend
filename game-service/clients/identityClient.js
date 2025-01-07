const axios = require("axios");

const IDENTITY_SERVICE_URL = process.env.IDENTITY_SERVICE_URL;

class IdentityClient {
	static async verifyToken(accessToken) {
		try {
			const response = await axios.get(
				`${IDENTITY_SERVICE_URL}/identity/auth/jwt-introspect?token=${accessToken}`
			);
			const data = await response.data;
			return data;
		} catch (error) {
			console.error(
				"Error verifying token:",
				error.response?.data || error.message
			);
			throw new Error("Token verification failed");
		}
	}
}
module.exports = IdentityClient;
