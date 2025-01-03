
const brandVerifyHandler = (req, res, next) => {
    if (req.tokenData == null || req.tokenData.role !== "BRAND") {
      return res.status(401).json({ error: 'Permission denied' });
    }  
    next()
  };
  
  module.exports = brandVerifyHandler;
  