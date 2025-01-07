const moment = require("moment");

function convertTimeQueryParamToFilter(query) {
	const filter = {};
	const { type, startDate, endDate } = query;

	if (type === "today") {
		const startOfDay = moment().startOf("day").toDate();
		const endOfDay = moment().endOf("day").toDate();

		filter.createdAt = { $gte: startOfDay, $lte: endOfDay };
	} else if (type === "month") {
		const startOfMonth = moment().startOf("month").toDate();
		const endOfMonth = moment().endOf("mo nth").toDate();

		filter.createdAt = { $gte: startOfMonth, $lte: endOfMonth };
	} else if (type === "year") {
		const startOfYear = moment().startOf("year").toDate();
		const endOfYear = moment().endOf("year").toDate();

		filter.createdAt = { $gte: startOfYear, $lte: endOfYear };
	} else if (type === "interval") {
		if (!startDate || !endDate) {
			throw new Error(
				"Both startDate and endDate are required for 'interval' type"
			);
		}

		const start = moment(startDate, "YYYY-MM-DD").startOf("day").toDate();
		const end = moment(endDate, "YYYY-MM-DD").endOf("day").toDate();

		filter.createdAt = { $gte: start, $lte: end };
	}
	return filter;
}

module.exports = { convertTimeQueryParamToFilter };
