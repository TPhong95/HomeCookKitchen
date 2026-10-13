import {useRef, useState} from "react";
import './chefprofile.css'
import {Banner} from "../general/banner.tsx";

/*
interface Chef {
  id: number;
  name: string;
  street: string;
  postalCode: string; // Due to postal codes starting with null. Convert in backend if needed
  email: string;
}
 */

export function ChefProfile() {
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

  // Modal
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
    <div className={"profile-layout"}>
      <Banner />
      <div className={"chef-wrapper"}>
        <div className={"chef-top-banner"}>
          {name ? (<h1>Welcome, {name}</h1>
          ) : (
            <h1>Welcome!</h1>
            )}
          <button
            className={"edit-profile-btn"}
            onClick={handleEditProfileModal}
          >
            Edit profile
          </button>
        </div>
        <div className={"contact-info"}>
          <p><strong>Name: </strong>{name}</p>
          <p><strong>Address:</strong></p>
          <ul className={"address-list"}>
            <li><strong>Street: </strong>{street}</li>
            <li><strong>Postcode: </strong>{postalCode}</li>
          </ul>
          <p><strong>Email: </strong>{email}</p>
        </div>

        <dialog
          className={"edit-profile-dialog"}
          ref={editProfileDialogRef}
        >
          <div className={"modal-contact-details"}>
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
          <div className={"save-changes-box"}>

            <button
              className={"yes-btn"}
              onClick={() => handleSaveChanges()}
            >
              Yes
            </button>
            <button
              className={"no-btn"}
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