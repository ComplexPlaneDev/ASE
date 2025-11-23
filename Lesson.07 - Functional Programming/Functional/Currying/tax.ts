#!/usr/bin/env ts-node

type Region = 'US' | 'EU' | 'ASIA';
type ProductType = 'digital' | 'physical' | 'service';
type CustomerType = 'business' | 'individual';

type TaxCalculator = (region: Region) => (product: ProductType) => (customer: CustomerType) => (amount: number) => number;

// -------------------------------
// 2. Base tax rates (can be loaded from config or API)
// -------------------------------

const taxRates: Record<Region, Record<ProductType, Record<CustomerType, number>>> = {
    US: {
        digital: { business: 0.05, individual: 0.1 },
        physical: { business: 0.06, individual: 0.12 },
        service: { business: 0.04, individual: 0.1 },
    },
    EU: {
        digital: { business: 0.0, individual: 0.2 },
        physical: { business: 0.0, individual: 0.2 },
        service: { business: 0.0, individual: 0.2 },
    },
    ASIA: {
        digital: { business: 0.03, individual: 0.08 },
        physical: { business: 0.04, individual: 0.1 },
        service: { business: 0.05, individual: 0.09 },
    }
};

// -------------------------------
// 3. Curried tax calculator
// -------------------------------

const calculateTax: TaxCalculator =
    (region) =>
    (product) =>
    (customer) =>
    (amount) => {
        const rate = taxRates[region]?.[product]?.[customer];
        if (rate === undefined) {
            throw new Error(`No tax rule found for ${region}/${product}/${customer}`);
        }
        return amount * rate;
    };

// -------------------------------
// 4. Build reusable partial calculators using currying
// -------------------------------

// Fix region first
const euTax = calculateTax('EU');
const usTax = calculateTax('US');

// Fix product + region
const euDigitalTax = euTax('digital');
const usPhysicalTax = usTax('physical');

// Fix customer type too
const euDigitalForIndividuals = euDigitalTax('individual');
const usPhysicalForBusinesses = usPhysicalTax('business');

// -------------------------------
// 5. Use it in code
// -------------------------------

console.log('EU Digital Individual - $100:', euDigitalForIndividuals(100));
console.log('EU Digital Business - $100:', euDigitalTax('business')(100));

console.log('US Physical Business - $200:', usPhysicalForBusinesses(200));
console.log('US Physical Individual - $200:', usPhysicalTax('individual')(200));

// Try a custom path
console.log('US Service Individual - $500:', calculateTax('US')('service')('individual')(500));
