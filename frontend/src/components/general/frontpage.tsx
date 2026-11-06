import styles from "./frontpage.module.css"
import {useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";
import {useUserContext} from "../context/useUserContext.tsx";

interface ExistingChef {
  id: number;
  name: string;
}

export function FrontPage() {
  const {login} = useUserContext();

  const navigate = useNavigate();

  const [existingChefs, setExistingChefs] = useState<ExistingChef[]>([]);
  const [selectedChefId, setSelectedChefId] = useState<number | "">("");

  useEffect(() => {
    async function loadExistingUsers() {
      try {
        const [
          chefRes,
          /* customerRes, */
        ] = await Promise.all([
          fetch("http://localhost:8100/api/chef"),
          /* fetch("http://localhost:8100/api/customer/all") */
        ]);

        if (!chefRes.ok) throw new Error(`Chef request failed: ${chefRes.status}`);

        const chefData: ExistingChef[] = await chefRes.json();
        setExistingChefs(chefData.map(chef => ({id: chef.id, name: chef.name})));
      } catch (error) {
        console.log(error);
      }
    }

    loadExistingUsers();
  }, []);

  function handleLoginWithExistingChef() {
    if (selectedChefId === "") return;
    login({id: selectedChefId, role: "chef"});
    navigate("/user/chef/profile");
  }

  return (
    <div className={styles["frontpage-wrapper"]}>
      <div className={styles["frontpage-top-banner"]}>
        <h1>Test landing page</h1>
      </div>
      <div className={styles["frontpage-description"]}>
        <p>The purpose of this page is to allow the user three options:</p>
        <ol>
          <li>Log in with an existing chef</li>
          <li>Log in with an existing customer (not yet implemented)</li>
          <li>Creating own user (not yet implemented)</li>
        </ol>
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
          <select disabled>
            <option value={""}>(not implemented)</option>
          </select>
          <button className={styles["login-btn"]} disabled>
            Login with customer (not yet)
          </button>
        </div>
      </div>
    </div>
  )
}
