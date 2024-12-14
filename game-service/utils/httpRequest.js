


const extractBearerToken = (req) => {
    const authHeader = req.headers['authorization'];
    if (authHeader == null) {
        return ""
    }
    // Extract the token from the header
    const accessToken = authHeader.split(' ')[1];
    return accessToken
}

module.exports = { extractBearerToken}