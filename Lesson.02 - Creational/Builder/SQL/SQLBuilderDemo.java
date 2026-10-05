/**
 * Client / Demo
 *
 * Demonstrates building SQL queries with fluent method chaining:
 *
 *     new SQLQuery.Builder("employees")
 *         .select("name", "salary")
 *         .where("department = 'Engineering'")
 *         .orderByDesc("salary")
 *         .limit(10)
 *         .build();
 *
 * Each method returns 'this' → natural, readable query construction.
 */
public class SQLBuilderDemo {

    public static void main(String[] args) {
        System.out.println("========= Fluent Builder Demo: SQL Query Builder =========\n");

        // ─── 1. Simple SELECT ──────────────────────────────────────────
        System.out.println(">>> 1. Simple query — all employees in Engineering\n");

        SQLQuery query1 = new SQLQuery.Builder("employees")
                .select("id", "name", "email", "salary")
                .whereEquals("department", "Engineering")
                .orderBy("name")
                .build();

        System.out.println(query1.toSQL());

        // ─── 2. Filtered + paginated ──────────────────────────────────
        System.out.println("\n>>> 2. Top 5 highest-paid senior employees\n");

        SQLQuery query2 = new SQLQuery.Builder("employees")
                .select("name", "position", "salary")
                .whereGreaterThan("salary", 80000)
                .whereLike("position", "%Senior%")
                .whereNotNull("manager_id")
                .orderByDesc("salary")
                .limit(5)
                .build();

        System.out.println(query2.toSQL());

        // ─── 3. JOIN query ─────────────────────────────────────────────
        System.out.println("\n>>> 3. Employees with their department and office\n");

        SQLQuery query3 = new SQLQuery.Builder("employees e")
                .select("e.name", "d.department_name", "o.city")
                .innerJoin("departments d", "e.department_id = d.id")
                .leftJoin("offices o", "d.office_id = o.id")
                .whereIn("d.department_name", "Engineering", "Product", "Design")
                .whereGreaterThan("e.salary", 50000)
                .orderBy("d.department_name")
                .orderBy("e.name")
                .build();

        System.out.println(query3.toSQL());

        // ─── 4. GROUP BY with HAVING ───────────────────────────────────
        System.out.println("\n>>> 4. Departments with average salary above 70k\n");

        SQLQuery query4 = new SQLQuery.Builder("employees e")
                .select("d.department_name", "COUNT(*) AS headcount", "AVG(e.salary) AS avg_salary")
                .innerJoin("departments d", "e.department_id = d.id")
                .groupBy("d.department_name")
                .having("AVG(e.salary) > 70000")
                .orderByDesc("avg_salary")
                .build();

        System.out.println(query4.toSQL());

        // ─── 5. DISTINCT + pagination ──────────────────────────────────
        System.out.println("\n>>> 5. Distinct cities with offices (page 2, 10 per page)\n");

        SQLQuery query5 = new SQLQuery.Builder("offices")
                .select("city", "country")
                .distinct()
                .whereNotNull("city")
                .orderBy("country")
                .orderBy("city")
                .limit(10)
                .offset(10)
                .build();

        System.out.println(query5.toSQL());

        // ─── 6. Minimal query (select all) ─────────────────────────────
        System.out.println("\n>>> 6. Minimal — SELECT * FROM products\n");

        SQLQuery query6 = new SQLQuery.Builder("products")
                .build();

        System.out.println(query6.toSQL());

        // ─── 7. Validation: HAVING without GROUP BY ────────────────────
        System.out.println("\n>>> 7. Invalid: HAVING without GROUP BY — should throw\n");

        try {
            SQLQuery invalid = new SQLQuery.Builder("orders")
                    .select("customer_id", "SUM(total)")
                    .having("SUM(total) > 1000")
                    .build();
        } catch (IllegalStateException e) {
            System.out.println("[Caught] " + e.getMessage());
        }
    }
}
