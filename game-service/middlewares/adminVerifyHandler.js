
const adminHandler = (req, res, next) => {
  if (req.tokenData == null || req.tokenData.role !== "ADMIN") {
    return res.status(401).json({ error: 'Permission denied' });
  }  
  next()
};

module.exports = adminHandler;
