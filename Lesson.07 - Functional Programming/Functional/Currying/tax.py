#!/usr/bin/env python3

# -------------------------------
# 1. Define tax rules as nested dictionaries
# -------------------------------

tax_rates = {
    "US": {
        "digital": {"business": 0.05, "individual": 0.10},
        "physical": {"business": 0.06, "individual": 0.12},
        "service": {"business": 0.04, "individual": 0.10},
    },
    "EU": {
        "digital": {"business": 0.0, "individual": 0.20},
        "physical": {"business": 0.0, "individual": 0.20},
        "service": {"business": 0.0, "individual": 0.20},
    },
    "ASIA": {
        "digital": {"business": 0.03, "individual": 0.08},
        "physical": {"business": 0.04, "individual": 0.10},
        "service": {"business": 0.05, "individual": 0.09},
    }
}

# -------------------------------
# 2. Curried tax calculator
# -------------------------------

def calculate_tax(region):
    def by_product(product):
        def by_customer(customer):
            def for_amount(amount):
                try:
                    rate = tax_rates[region][product][customer]
                except KeyError:
                    raise ValueError(f"No tax rule found for {region}/{product}/{customer}")
                return amount * rate
            return for_amount
        return by_customer
    return by_product

# -------------------------------
# 3. Build reusable partial calculators using currying
# -------------------------------

# Fix region
eu_tax = calculate_tax("EU")
us_tax = calculate_tax("US")

# Fix product + region
eu_digital_tax = eu_tax("digital")
us_physical_tax = us_tax("physical")

# Fix customer type too
eu_digital_for_individuals = eu_digital_tax("individual")
us_physical_for_businesses = us_physical_tax("business")

# -------------------------------
# 4. Use it in code
# -------------------------------

print("EU Digital Individual - $100:", eu_digital_for_individuals(100))   # 20%
print("EU Digital Business - $100:", eu_digital_tax("business")(100))      # 0%
print("US Physical Business - $200:", us_physical_for_businesses(200))      # 6%
print("US Physical Individual - $200:", us_physical_tax("individual")(200)) # 12%

# Try a custom path
print("US Service Individual - $500:",
      calculate_tax("US")("service")("individual")(500))  # 10%
