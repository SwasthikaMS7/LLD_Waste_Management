package waste_management_system;

import java.util.*;

enum Role { ADMIN, STAFF, CUSTOMER }
enum Status { PENDING, IN_PROGRESS, COMPLETED }

class User {
    int id;
    String name;
    Role role;

    public User(int id, String name, Role role) {
        this.id = id;
        this.name = name;
        this.role = role;
    }
}

class WasteCategory {
    int id;
    String name;

    public WasteCategory(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "WasteCategory [id=" + id + ", name=" + name + "]";
    }
}

class WasteCollection {
    int id;
    int customerId;
    int categoryId;
    Status status;
    Date date; // ✅ Added date

    public WasteCollection(int id, int customerId, int categoryId) {
        this.id = id;
        this.customerId = customerId;
        this.categoryId = categoryId;
        this.status = Status.PENDING;
        this.date = new Date(); // ✅ Initialize date
    }

    @Override
    public String toString() {
        return "WasteCollection [id=" + id +
                ", customerId=" + customerId +
                ", categoryId=" + categoryId +
                ", status=" + status +
                ", date=" + date + "]";
    }
}

class WasteManagementSystem {
    List<User> users = new ArrayList<>();
    List<WasteCategory> categories = new ArrayList<>();
    List<WasteCollection> collection = new ArrayList<>();

    void addCategory(WasteCategory c) {
        categories.add(c);
    }

    void deleteCategory(int cid) {
        categories.removeIf(c -> c.id == cid);
    }

    void viewCollections() {
        if (collection.isEmpty()) {
            System.out.println("No collections found.");
            return;
        }
        collection.forEach(System.out::println);
    }

    void registerCollection(int customerId, int categoryId) {
        int id = collection.size() + 1;
        collection.add(new WasteCollection(id, customerId, categoryId));
    }

    void updateStatus(int id, Status s) {
        boolean found = false;
        for (WasteCollection wc : collection) {
            if (wc.id == id) {
                wc.status = s;
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Collection ID not found.");
        }
    }

    void requestPickup(int customerId, int categoryId) {
        int id = collection.size() + 1;
        collection.add(new WasteCollection(id, customerId, categoryId));
    }

    void viewHistory(int customerId) {
        boolean found = false;
        for (WasteCollection wc : collection) {
            if (wc.customerId == customerId) {
                System.out.println(wc);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No history found.");
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        WasteManagementSystem sys = new WasteManagementSystem();

        User admin = new User(1, "Admin", Role.ADMIN);
        User staff = new User(2, "Staff", Role.STAFF);
        User customer = new User(3, "Customer", Role.CUSTOMER);

        sys.users.add(admin);
        sys.users.add(staff);
        sys.users.add(customer);

        while (true) {
            System.out.println("\n==== Choose Role ====");
            System.out.println("1.Admin");
            System.out.println("2.Staff");
            System.out.println("3.Customer");
            System.out.println("4.Exit");
            System.out.print("Choose Role: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("1.Add Category");
                    System.out.println("2.Delete Category");
                    System.out.println("3.View Collections");
                    int adminChoice = scanner.nextInt();

                    if (adminChoice == 1) {
                        System.out.print("Enter Category id: ");
                        int cid = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Enter Category name: ");
                        String cname = scanner.nextLine();

                        sys.addCategory(new WasteCategory(cid, cname));
                        System.out.println("Category Added.");
                    } 
                    else if (adminChoice == 2) {
                        System.out.print("Enter category id to delete: ");
                        int cid = scanner.nextInt();
                        sys.deleteCategory(cid);
                        System.out.println("Category Deleted.");
                    } 
                    else if (adminChoice == 3) {
                        sys.viewCollections();
                    }
                    break;

                case 2:
                    System.out.println("1.Register Collection");
                    System.out.println("2.Update Status");
                    int staffChoice = scanner.nextInt();

                    if (staffChoice == 1) {
                        System.out.print("Enter customer id: ");
                        int custId = scanner.nextInt();
                        System.out.print("Enter Category id: ");
                        int catId = scanner.nextInt();

                        sys.registerCollection(custId, catId);
                        System.out.println("Collection Registered.");
                    } 
                    else if (staffChoice == 2) {
                        System.out.print("Enter collection id: ");
                        int colId = scanner.nextInt();

                        System.out.println("Choose status: 1.Pending 2.In Progress 3.Completed");
                        int s = scanner.nextInt();

                        Status status = (s == 1) ? Status.PENDING :
                                        (s == 2) ? Status.IN_PROGRESS :
                                                   Status.COMPLETED;

                        sys.updateStatus(colId, status);
                        System.out.println("Status Updated.");
                    }
                    break;

                case 3:
                    System.out.println("1.Request Pickup");
                    System.out.println("2.View History");
                    int custChoice = scanner.nextInt();

                    if (custChoice == 1) {
                        System.out.print("Enter Customer id: ");
                        int cid = scanner.nextInt();
                        System.out.print("Enter Category id: ");
                        int catId = scanner.nextInt();

                        sys.requestPickup(cid, catId);
                        System.out.println("Pickup Requested.");
                    } 
                    else if (custChoice == 2) {
                        System.out.print("Enter Customer id: ");
                        int cid = scanner.nextInt();
                        sys.viewHistory(cid);
                    }
                    break;

                case 4:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}