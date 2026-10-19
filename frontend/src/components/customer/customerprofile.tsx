import {useRef, useState} from "react";
import styles from './customerprofile.module.css'
import {Banner} from "../general/banner.tsx";

export function CustomerProfile() {
  // Main values
  const [name, setName] = useState("");
  const [street, setStreet] = useState("");
  const [postalCode, setPostalCode] = useState("");
  const [email, setEmail] = useState("");

  // Temporary values for editing
  const [tempName, setTempName] = useState("");
  const [tempStreet, setTempStreet] = useState("");
  const [tempPostalCode, setTempPostalCode] = useState("");
  const [tempEmail, setTempEmail] = useState("");

  // Modals
  const editProfileDialogRef = useRef<HTMLDialogElement>(null);

  const handleEditProfileModal = () => {
    setTempName(name);
    setTempStreet(street);
    setTempPostalCode(postalCode);
    setTempEmail(email);

    editProfileDialogRef.current?.showModal();
  };

  function handleSaveChanges() {
    setName(tempName);
    setStreet(tempStreet);
    setPostalCode(tempPostalCode);
    setEmail(tempEmail);
    editProfileDialogRef.current?.close();
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
          <p><strong>Address:</strong></p>
          <ul className={styles["address-list"]}>
            <li><strong>Street: </strong>{street}</li>
            <li><strong>Postcode: </strong>{postalCode}</li>
          </ul>
          <p><strong>Email: </strong>{email}</p>
        </div>

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
            <div>Street:</div>
            <input
              type="text"
              value={tempStreet}
              onChange={(e) => setTempStreet(e.target.value)}
            />
            <div>Postal Code:</div>
            <input
              type="text"
              value={tempPostalCode}
              onChange={(e) => setTempPostalCode(e.target.value)}
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