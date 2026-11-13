import styles from "./frontpage.module.css"
import {useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";
import {useUserContext} from "../context/useUserContext.tsx";

interface ExistingChef {
  id: number;
  name: string;
}

interface ExistingCustomer {
  customerId: number;
  name: string;
}

export function FrontPage() {
  const {login} = useUserContext();

  const navigate = useNavigate();

  const [existingChefs, setExistingChefs] = useState<ExistingChef[]>([]);
  const [existingCustomers, setExistingCustomers] = useState<ExistingCustomer[]>([]);
  const [selectedChefId, setSelectedChefId] = useState<number | "">("");
  const [selectedCustomerId, setSelectedCustomerId] = useState<number | "">("");

  useEffect(() => {
    async function loadExistingUsers() {
      try {
        const [
          chefRes,
          customerRes,
        ] = await Promise.all([
          fetch("http://localhost:8100/api/chef"),
          fetch("http://localhost:8100/api/customer/all"),
        ]);

        if (chefRes.ok) {
          const chefData: ExistingChef[] = await chefRes.json();
          setExistingChefs(chefData.map(chef => ({id: chef.id, name: chef.name})));
        } else {
          console.log(`Chef request failed: ${chefRes.status}`);
        }

        if (customerRes.ok) {
          const customerData: ExistingCustomer[] = await customerRes.json();
          setExistingCustomers(customerData.map(customer => ({customerId: customer.customerId, name: customer.name})));
        } else {
          console.log(`Customer request failed: ${customerRes.status}`);
        }

      } catch (error) {
        console.log(error);
      }
    }

    loadExistingUsers();
  }, []);

  function handleLoginWithExistingChef() {
    if (selectedChefId === "") return;
    login({id: selectedChefId, role: "chef"});
    navigate("/chef/profile");
  }

  function handleLoginWithExistingCustomer() {
    if (selectedCustomerId === "") return;
    login({id: selectedCustomerId, role: "customer"});
    navigate("/customer/profile");
  }

  return (
    <div className={styles["frontpage-wrapper"]}>
      <div className={styles["frontpage-top-banner"]}>
        <h1>Welcome to our exam!</h1>
      </div>
      <div className={styles["frontpage-description"]}>
        <p>
          You can log in as a test user, either as a chef or
          as a customer. Session cookies are set upon logging in.
          It might take a couple of seconds to populate the
          dropdown menus from the backend :)
        </p>
      </div>
      <div className={styles["existing-user-wrapper"]}>
        <div className={`${styles["existing-user-box"]} ${styles["existing-chef-box"]}`}>
          <h2 className={styles["user-title"]}>Chef</h2>
          <select
            value={selectedChefId}
            onChange={(e) => setSelectedChefId(e.target.value ? Number(e.target.value) : "")}
          >
            <option value={""}>-- Select a chef --</option>
            {existingChefs.map((c: ExistingChef) => (
              <option key={c.id} value={c.id}>{c.name}</option>
            ))}
          </select>
          <button
            className={styles["login-btn"]}
            onClick={handleLoginWithExistingChef}
            disabled={selectedChefId === ""}
          >
            Login with chef
          </button>
        </div>
        <div className={`${styles["existing-user-box"]} ${styles["existing-customer-box"]}`}>
          <h2 className={styles["user-title"]}>Customer</h2>
          <select
            value={selectedCustomerId}
            onChange={(e) => setSelectedCustomerId(e.target.value ? Number(e.target.value) : "")}
          >
            <option value={""}>
              {existingCustomers.length === 0 ? "-- No customers found --" : "-- Select a customer --"}
            </option>
            {existingCustomers.map((c: ExistingCustomer) => (
              <option key={c.customerId} value={c.customerId}>{c.name}</option>
            ))}
          </select>
          <button
            className={styles["login-btn"]}
            onClick={handleLoginWithExistingCustomer}
            disabled={selectedCustomerId === ""}
          >
            Login with customer
          </button>
        </div>
      </div>
    </div>
  )
}
