import { useRef, useState} from "react";
import './chefprofile.css'

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
    <>
      <div className={"wrapper"}>
        <div className={"top-banner"}>
          <h1>Welcome {name}</h1>
          <button onClick={handleEditProfileModal}>Edit profile</button>
        </div>
        <div className={"contact-info"}>
          <p>Name: {name}</p>
          <ul>
            <li>Street: {street}</li>
            <li>Postcode: {postalCode}</li>
          </ul>
          <p>Email: {email}</p>
        </div>
        <div className={"useful-links"}>
          <div className={"link-box"}>Listings</div>
          <div className={"link-box"}>Recipes</div>
          <div className={"link-box"}>History</div>
        </div>

        <dialog ref={editProfileDialogRef}>
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
          <div className={"save-changes-box"}>
            <p>Save changes?</p>
            <button onClick={() => handleSaveChanges()}>Yes</button>
            <button onClick={() => editProfileDialogRef.current?.close()}>No</button>
          </div>
        </dialog>
      </div>
    </>
  )
}