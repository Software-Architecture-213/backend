module.exports = (req, res, next) => {
	res.ok = (data) => {
		res.status(200).json({ data });
	};

	res.created = (data) => {
		res.status(201).json({ data });
	};

	res.noContent = () => {
		res.status(204).send();
	};

	res.badRequest = (error) => {
		res.status(400).json({ error });
	};

	res.unauthorized = (error) => {
		res.status(401).json({ error });
	};

	res.forbidden = (error) => {
		res.status(403).json({ error });
	};

	res.notFound = (error) => {
		res.status(404).json({ error });
	};

	res.error = (error) => {
		res.status(500).json({ error });
	};

	next();
};
