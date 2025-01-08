const IdentityClient = require("../clients/identityClient");
const { extractBearerToken } = require("../utils/httpRequest");

const jwtVerifyHandler = async (req, res, next) => {
    try {
        // Get the Authorization header
        const accessToken = extractBearerToken(req)
        if (accessToken == null || accessToken === "") {
            return res.status(401).json({ error: 'Authorization token missing or emty' });
        }
        const tokenData = await IdentityClient.verifyToken(accessToken);
        /* 
        tokenData:  {
            message: "Token validation successful.",
            userId: "mn8ZKzUyBAWQnxwcJRTQSo3c5U62",
            role: "USER",
            validated: true,
        }
        */
		if (tokenData == null || tokenData.isValidated == false) {
			res.status(401).json({ error: "Invalid or expired token" });
		}
		req.tokenData = tokenData;

		next();
	} catch (error) {
		console.error("JWT Verification Error:", error.message);
		res.status(401).json({ error: "Invalid or expired token" });
	}
};

module.exports = jwtVerifyHandler;
