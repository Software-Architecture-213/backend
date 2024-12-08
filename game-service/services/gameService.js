const Game = require("../models/Game");
const CustomError = require("../exceptions/CustomError");

class GameService {
  static async getGames() {
    const games = await Game.find();
    if (!games.length) {
      throw new CustomError(404, "No games found");
    }
    return games;
  }
}

module.exports = GameService;
