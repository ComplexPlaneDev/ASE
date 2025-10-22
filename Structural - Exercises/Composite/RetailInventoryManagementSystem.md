# Retail Inventory Management System

## Problem Description

You are developing an inventory management system for a retail store. The system needs to handle both individual products and product bundles in a unified manner. Products can be grouped into bundles, and bundles can contain other bundles, creating complex product hierarchies.

## Requirements

The system should handle:

1. **Individual Products**: Each product has a name, SKU (Stock Keeping Unit), price, quantity in stock, and category
2. **Product Bundles**: Collections of products or other bundles sold together (e.g., "Office Supplies Bundle", "Electronics Starter Pack")
3. **Nested Bundles**: Bundles can contain other bundles (e.g., "Complete Home Office" bundle containing "Office Supplies" and "Tech Essentials" sub-bundles)

## Expected Functionality

Your solution should support the following operations uniformly across all items (whether individual products or bundles):

- `displayInfo()`: Show information about the product(s)
- `calculateTotalPrice()`: Return the total price of all products
- `checkAvailability()`: Check if all products are in stock
- `updateStock(String sku, int newQuantity)`: Update stock quantity for a specific product

## Example Structure

```
Retail Inventory
├── Office Supplies Bundle
│   ├── Desk Accessories
│   │   ├── Pen Set (SKU: PEN001, $15.99, 50 units, Stationery)
│   │   └── Stapler (SKU: STA001, $24.99, 25 units, Stationery)
│   └── Paper Products
│       ├── Notebook (SKU: NOT001, $8.99, 100 units, Stationery)
│       └── Sticky Notes (SKU: STI001, $3.99, 200 units, Stationery)
└── Electronics Bundle
    ├── Wireless Mouse (SKU: MOU001, $29.99, 30 units, Electronics)
    └── Tech Essentials
        ├── USB Cable (SKU: USB001, $12.99, 75 units, Electronics)
        └── Power Bank (SKU: POW001, $39.99, 15 units, Electronics)
```

## Expected Behavior

When calling `displayInfo()` on:
- A single product: Shows product name, SKU, price, stock quantity, and category
- A bundle: Shows bundle name and recursively displays all contained products
- The main inventory: Shows the entire inventory hierarchy

When calling `calculateTotalPrice()` on:
- A single product: Returns the product's price
- A bundle: Returns the sum of prices for all products in the bundle and sub-bundles
- The main inventory: Returns total value of all products in inventory

When calling `checkAvailability()` on:
- A single product: Returns true if quantity > 0, false otherwise
- A bundle: Returns true only if ALL products in the bundle and sub-bundles are available
- The main inventory: Returns true only if ALL products are available

## Task

Design and implement a Java solution that allows treating individual products and product bundles uniformly while maintaining the hierarchical structure. The solution should be easily extensible to add new product types and bundle configurations.
