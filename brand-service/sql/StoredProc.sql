CREATE OR REPLACE FUNCTION GetBrandCreationCount(
    startDate DATE,
    endDate DATE
)
RETURNS TABLE (
    id TEXT,
    brandCount INTEGER
) AS $$
BEGIN
    -- Generate a series of dates between startDate and endDate
    RETURN QUERY
    WITH date_series AS (
        SELECT generate_series(startDate, endDate, INTERVAL '1 day')::DATE AS date
    ),
    brand_counts AS (
        SELECT 
            DATE(created_at) AS creationDate,
            COUNT(*)::INTEGER AS count
        FROM Brands
        WHERE created_at >= startDate AND created_at <= endDate
        GROUP BY DATE(created_at)
    )
    SELECT 
        ds.date::TEXT AS id,
        COALESCE(bc.count, 0) AS brandCount
    FROM date_series ds
    LEFT JOIN brand_counts bc
    ON ds.date = bc.creationDate
    ORDER BY ds.date;
END;
$$ LANGUAGE plpgsql;


-- Daily voucher stats
CREATE OR REPLACE FUNCTION getDailyVoucherStats(
    brandIdInput VARCHAR,
    startDate DATE,
    endDate DATE
) RETURNS TABLE (
    id TEXT,
    voucherCount INTEGER
) AS $$
BEGIN
    RETURN QUERY
    -- Generate date series for the full range
    WITH date_series AS (
        SELECT generate_series(startDate, endDate, INTERVAL '1 day')::DATE AS date
    ),
    -- Calculate voucher counts for dates where they exist
    voucher_counts AS (
        SELECT 
            DATE(v.created_at) AS usage_date,
            COUNT(vu.id) AS count
        FROM vouchers v
        JOIN promotions p ON v.promotion_id = p.id
        LEFT JOIN voucher_user vu ON vu.voucher_id = v.id
        WHERE p.brand_id = brandIdInput
        AND DATE(v.created_at) BETWEEN startDate AND endDate
        GROUP BY DATE(v.created_at)
    )
    -- Join date series with voucher counts to include all dates
    SELECT 
        ds.date::TEXT AS id,
        COALESCE(vc.count, 0)::INTEGER AS voucherCount
    FROM date_series ds
    LEFT JOIN voucher_counts vc ON ds.date = vc.usage_date
    ORDER BY ds.date;
END;
$$ LANGUAGE plpgsql;