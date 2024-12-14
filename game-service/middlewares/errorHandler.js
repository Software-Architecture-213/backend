const errorHandler = (err, req, res, next) => {
    console.error(err.stack);
  
    if (err instanceof Error) {
      const errorCode = err.errorCode || 500;
      const message = err.message || "Internal Server Error";
      return res.status(errorCode).json({ errorCode, message });
    }
  
    res.status(500).json({
      errorCode: 500,
      message: "An unexpected error occurred",
    });
  };
  
  module.exports = errorHandler;
  
