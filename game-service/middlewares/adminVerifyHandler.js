const adminHandler = (req, res, next) => {
	console.log(req.tokenData);
	if (req.tokenData == null || req.tokenData.role !== "ADMIN") {
		return res.status(401).json({ error: "Permission denied" });
	}
	next();
};

module.exports = adminHandler;
