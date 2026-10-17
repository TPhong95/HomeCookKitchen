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

  const [description, setDescription] = useState("");
  const [music, setMusic] = useState(false);
  const [pets, setPets] = useState(false);
  const [outdoor, setOutdoor] = useState(false);
  const [familyFriendly, setFamilyFriendly] = useState(false);

  // Temporary values for editing
  const [tempName, setTempName] = useState("");
  const [tempStreet, setTempStreet] = useState("");
  const [tempPostalCode, setTempPostalCode] = useState("");
  const [tempEmail, setTempEmail] = useState("");

  const [tempDescription, setTempDescription] = useState("");
  const [tempMusic, setTempMusic] = useState(false);
  const [tempPets, setTempPets] = useState(false);
  const [tempOutdoor, setTempOutdoor] = useState(false);
  const [tempFamilyFriendly, setTempFamilyFriendly] = useState(false);

  // Modals
  const editProfileDialogRef = useRef<HTMLDialogElement>(null);
  const editHomeDescriptionDialogRef = useRef<HTMLDialogElement>(null);

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

  const handleEditHomeDescriptionModal = () => {
    setTempDescription(description);
    setTempMusic(music);
    setTempPets(pets);
    setTempOutdoor(outdoor);
    setTempFamilyFriendly(familyFriendly);
    editHomeDescriptionDialogRef.current?.showModal();
  };

  const handleSaveHomeDescription = () => {
    setDescription(tempDescription);
    setMusic(tempMusic);
    setPets(tempPets);
    setOutdoor(tempOutdoor);
    setFamilyFriendly(tempFamilyFriendly);
    editHomeDescriptionDialogRef.current?.close();
  };

  return (
    <div className={"profile-layout"}>
      <Banner/>
      <div className={"chef-wrapper"}>
        <div className={"chef-top-banner"}>
          {name ? (<h1>Welcome, {name}!</h1>
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
        <h1>Contact Info</h1>
        <div className={"contact-info"}>
          <p><strong>Name: </strong>{name}</p>
          <p><strong>Address:</strong></p>
          <ul className={"address-list"}>
            <li><strong>Street: </strong>{street}</li>
            <li><strong>Postcode: </strong>{postalCode}</li>
          </ul>
          <p><strong>Email: </strong>{email}</p>
        </div>

        <div className={"vibe-section-header-banner"}>
          <h1>Home Description</h1>
          <button
            className={"edit-description-btn"}
            onClick={handleEditHomeDescriptionModal}
          >
            Edit description
          </button>
        </div>
        <div className={"contact-info"}>
          <p><strong>Description: </strong>{description}</p>
          <p><strong>Vibes:</strong></p>
          <ul className={"address-list"}>
            <li><strong>Music: </strong>{music}</li>
            <li><strong>Pets: </strong> {pets ? ("Yes") : ("No")}</li>
            <li><strong>Outdoor: </strong>{outdoor ? ("Yes") : ("No")}</li>
            <li><strong>Family Friendly: </strong>{familyFriendly ? ("Yes") : ("No")}</li>
          </ul>
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

        <dialog
          className={"edit-profile-dialog"}
          ref={editHomeDescriptionDialogRef}
        >
          <div className={"modal-contact-details"}>
            <div>Description:</div>
            <textarea
              value={tempDescription}
              onChange={(e) => setTempDescription(e.target.value)}
              rows={3}
              style={{ width: "100%", padding: "0.5rem", borderRadius: "4px", border: "1px solid #0a4c86" }}
            />
            <div style={{ marginTop: "1rem", display: "flex", flexDirection: "column", gap: "0.5rem" }}>
              <label style={{ display: "flex", alignItems: "center", gap: "0.5rem", fontWeight: "bold", color: "#1d3b58" }}>
                <input
                  type="checkbox"
                  checked={tempMusic}
                  onChange={(e) => setTempMusic(e.target.checked)}
                />
                Music
              </label>
              <label style={{ display: "flex", alignItems: "center", gap: "0.5rem", fontWeight: "bold", color: "#1d3b58" }}>
                <input
                  type="checkbox"
                  checked={tempPets}
                  onChange={(e) => setTempPets(e.target.checked)}
                />
                Pets
              </label>
              <label style={{ display: "flex", alignItems: "center", gap: "0.5rem", fontWeight: "bold", color: "#1d3b58" }}>
                <input
                  type="checkbox"
                  checked={tempOutdoor}
                  onChange={(e) => setTempOutdoor(e.target.checked)}
                />
                Outdoor
              </label>
              <label style={{ display: "flex", alignItems: "center", gap: "0.5rem", fontWeight: "bold", color: "#1d3b58" }}>
                <input
                  type="checkbox"
                  checked={tempFamilyFriendly}
                  onChange={(e) => setTempFamilyFriendly(e.target.checked)}
                />
                Family Friendly
              </label>
            </div>
          </div>
          <p>Save changes?</p>
          <div className={"save-changes-box"}>
            <button
              className={"yes-btn"}
              onClick={handleSaveHomeDescription}
            >
              Yes
            </button>
            <button
              className={"no-btn"}
              onClick={() => editHomeDescriptionDialogRef.current?.close()}
            >
              No
            </button>
          </div>
        </dialog>
      </div>
    </div>
  )
}