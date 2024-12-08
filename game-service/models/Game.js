const mongoose = require("mongoose")

const GameSchema = new mongoose.Schema({
    _id: { type: mongoose.Schema.Types.ObjectId, auto: true }, // Game ID  
    name: { type: String, required: true }, // Tên trò chơi  
    type: { 
      type: String, 
      enum: ['quiz', 'shake'], 
      required: true 
    }, // Loại trò chơi  
    imageUrl: { type: String }, // Hình ảnh đại diện trò chơi  
    description: { type: String }, // Giới thiệu trò chơi  
    guideline: { type: String },
    allowItemExchange: { type: Boolean, default: false }, // Cho phép trao đổi vật phẩm hay không  
    createdAt: { type: Date, default: Date.now }, // Ngày tạo trò chơi  
});

module.exports = mongoose.model("Games", GameSchema)