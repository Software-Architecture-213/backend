const { Binary } = require("mongodb");

const toUUID = (id) => {
	if (id instanceof Binary) return id;
	else
		return new Binary(
			Buffer.from(id.replace(/-/g, ""), "hex"),
			Binary.SUBTYPE_UUID
		);
};

module.exports = toUUID;
