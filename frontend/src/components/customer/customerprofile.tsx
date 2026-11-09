import {useEffect, useRef, useState} from "react";
import styles from './customerprofile.module.css'
import {Banner} from "../general/banner.tsx";
import {useUserContext} from "../context/useUserContext.tsx";
import {sendJson} from "../util/api.ts";
import toast from "react-hot-toast";

interface CustomerResponse {
  id: number;
  name: string;
  email: string;
}

export function CustomerProfile() {
  const {user} = useUserContext();
  const customerId = user?.role === "customer" ? user.id : null;

  // Main values
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");

  // Temporary values for editing
  const [tempName, setTempName] = useState("");
  const [tempEmail, setTempEmail] = useState("");

  // Modals
  const editProfileDialogRef = useRef<HTMLDialogElement>(null);

  useEffect(() => {
    if (customerId === null) return;

    async function loadCustomer(id: number) {
      try {
        const res = await fetch(`http://localhost:8100/api/customer/${id}`);
        if (!res.ok) throw new Error(`Customer request failed: ${res.status}`);

        const customer: CustomerResponse = await res.json();

        setName(customer.name);
        setEmail(customer.email);
      } catch (error) {
        toast.error(error instanceof Error ? error.message : "Something went wrong");
      }
    }

    loadCustomer(customerId);
  }, [customerId]);

  const handleEditProfileModal = () => {
    setTempName(name);
    setTempEmail(email);

    editProfileDialogRef.current?.showModal();
  };

  async function handleSaveChanges() {
    if (customerId === null) return;

    try {
      const updated = await sendJson<CustomerResponse>(
        "PATCH",
        `http://localhost:8100/api/customer/${customerId}`,
        {name: tempName, email: tempEmail}
      );

      setName(updated.name);
      setEmail(updated.email);
      toast.success("Updated contact info successfully");
      editProfileDialogRef.current?.close();
    } catch (error) {
      toast.error(error instanceof Error ? error.message : "Something went wrong");
    }
  }

  return (
    <div className={styles["profile-layout"]}>
      <Banner/>
      <div className={styles["customer-wrapper"]}>
        <div className={styles["customer-top-banner"]}>
          {name ? (<h1>Welcome, {name}!</h1>
          ) : (
            <h1>Welcome!</h1>
          )}

        </div>

        {customerId === null ? (
          <div className={styles["contact-info"]}>
            <p>Not logged in as a customer. Pick a customer on the front page.</p>
          </div>
        ) : (
          <>
            <div className={styles["vibe-section-header-banner"]}>
              <h1>Contact Info</h1>
              <button
                className={styles["edit-profile-description-btn"]}
                onClick={handleEditProfileModal}
              >
                Edit contact info
              </button>
            </div>
            <div className={styles["contact-info"]}>
              <p><strong>Name: </strong>{name}</p>
              <p><strong>Email: </strong>{email}</p>
            </div>
          </>
        )}

        <dialog
          className={styles["edit-profile-dialog"]}
          ref={editProfileDialogRef}
        >
          <div className={styles["modal-contact-details"]}>
            <div>Name:</div>
            <input
              type="text"
              value={tempName}
              onChange={(e) => setTempName(e.target.value)}
            />
            <div>Email:</div>
            <input
              type="text"
              value={tempEmail}
              onChange={(e) => setTempEmail(e.target.value)}
            />
          </div>
          <p className={""}>Save changes?</p>
          <div className={styles["save-changes-box"]}>

            <button
              className={styles["yes-btn"]}
              onClick={() => handleSaveChanges()}
            >
              Yes
            </button>
            <button
              className={styles["no-btn"]}
              onClick={() => editProfileDialogRef.current?.close()}
            >
              No
            </button>
          </div>
        </dialog>

      </div>
    </div>
  )
}