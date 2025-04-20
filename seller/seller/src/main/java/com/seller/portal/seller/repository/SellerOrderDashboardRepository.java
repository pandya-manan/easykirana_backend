package com.seller.portal.seller.repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.seller.portal.seller.entity.SellerOrderDashboardDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SellerOrderDashboardRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<SellerOrderDashboardDTO> getSellerDashboardData(Long sellerId) {
        List<SellerOrderDashboardDTO> results = new ArrayList<>();
        
        String call = "{call GetSellerOrderDashboard(?)}";
        
        try (Connection conn = jdbcTemplate.getDataSource().getConnection();
             CallableStatement stmt = conn.prepareCall(call)) {
            
            stmt.setLong(1, sellerId);
            boolean hasResults = stmt.execute();
            
            if (hasResults) {
                try (ResultSet rs = stmt.getResultSet()) {
                    while (rs.next()) {
                        SellerOrderDashboardDTO dto = new SellerOrderDashboardDTO();
                        
                        // Map each column to DTO fields
                        dto.setSellerId(rs.getLong("seller_id"));
                        dto.setShopName(rs.getString("shop_name"));
                        dto.setOrderId(rs.getLong("order_id"));
                        dto.setOrderTrackingNumber(rs.getString("order_tracking_number"));
                        dto.setOrderStatus(rs.getString("order_status"));
                        dto.setOrderDate(rs.getDate("order_date"));
                        dto.setOrderTotal(rs.getBigDecimal("order_total"));
                        dto.setCustomerId(rs.getLong("customer_id"));
                        dto.setCustomerName(rs.getString("customer_name"));
                        dto.setCustomerEmail(rs.getString("customer_email"));
                        dto.setShippingStreet(rs.getString("shipping_street"));
                        dto.setShippingCity(rs.getString("shipping_city"));
                        dto.setShippingState(rs.getString("shipping_state"));
                        dto.setShippingCountry(rs.getString("shipping_country"));
                        dto.setShippingZip(rs.getString("shipping_zip"));
                        dto.setSellerCity(rs.getString("seller_city"));
                        dto.setSellerState(rs.getString("seller_state"));
                        dto.setSellerPinCode(rs.getString("seller_pin_code"));
                        dto.setProductsOrdered(rs.getString("products_ordered"));
                        dto.setTotalItemsFromSeller(rs.getInt("total_items_from_seller"));
                        dto.setTotalValueFromSeller(rs.getBigDecimal("total_value_from_seller"));
                        dto.setEstimatedDeliveryTime(rs.getString("estimated_delivery_time"));
                        
                        results.add(dto);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error calling stored procedure", e);
        }
        
        return results;
    }
}