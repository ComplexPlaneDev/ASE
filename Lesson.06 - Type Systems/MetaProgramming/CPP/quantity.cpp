#include <iostream>
#include <type_traits>

// Step 1: Define a dimension holder (exponents of fundamental dimensions)
template<int M, int S>
struct Dimension {
    static constexpr int meter = M;
    static constexpr int second = S;
};

// Step 2: Primary template for Quantity
template<typename Dim>
class Quantity {
private:
    double value;

public:
    constexpr explicit Quantity(double v) : value(v) {}

    constexpr double getValue() const { return value; }

    // Allow conversion between compatible dimensions
    template<typename OtherDim>
    friend class Quantity;

    // Add two quantities with the same dimension
    Quantity operator+(const Quantity& other) const {
        return Quantity(this->value + other.value);
    }

    // Subtract
    Quantity operator-(const Quantity& other) const {
        return Quantity(this->value - other.value);
    }

    // Multiply with another Quantity: dimensions are combined
    template<typename OtherDim>
    auto operator*(const Quantity<OtherDim>& other) const ->
        Quantity<Dimension<
            Dim::meter + OtherDim::meter,
            Dim::second + OtherDim::second
        >> {
        using ResultDim = Dimension<
            Dim::meter + OtherDim::meter,
            Dim::second + OtherDim::second
        >;
        return Quantity<ResultDim>(this->value * other.getValue());
    }

    // Divide: dimensions are subtracted
    template<typename OtherDim>
    auto operator/(const Quantity<OtherDim>& other) const ->
        Quantity<Dimension<
            Dim::meter - OtherDim::meter,
            Dim::second - OtherDim::second
        >> {
        using ResultDim = Dimension<
            Dim::meter - OtherDim::meter,
            Dim::second - OtherDim::second
        >;
        return Quantity<ResultDim>(this->value / other.getValue());
    }
};

// Step 3: Helper types for common units
using Meter = Dimension<1, 0>;
using Second = Dimension<0, 1>;
using MeterPerSecond = Dimension<1, -1>;

using Length = Quantity<Meter>;
using Time = Quantity<Second>;
using Speed = Quantity<MeterPerSecond>;

// Step 4: Literal operators
constexpr Quantity<Meter> operator"" _m(long double x) {
    return Quantity<Meter>(static_cast<double>(x));
}

constexpr Quantity<Second> operator"" _s(long double x) {
    return Quantity<Second>(static_cast<double>(x));
}

int main(int, char **) {
    Length d1 = 100.0_m;
    Length d2 = 50.0_m;
    Time t = 20.0_s;

    Length totalDistance = d1 + d2; // Valid
    std::cout << "Total distance: " << totalDistance.getValue() << " m\n";

    Speed speed = d1 / t; // Valid: m / s
    std::cout << "Speed: " << speed.getValue() << " m/s\n";

#ifdef INVALID_OPERATION
    auto invalid = d1 + t; // Can't add meters and seconds!
#endif

    return 0;
}
