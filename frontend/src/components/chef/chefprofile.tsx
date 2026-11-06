import {useEffect, useRef, useState} from "react";
import styles from './chefprofile.module.css'
import {Banner} from "../general/banner.tsx";
import {useUserContext} from "../context/useUserContext.tsx";
import {type ChefAddress, type ChefResponse, type ChefAddressWithDescription} from "../shared/chefinterface.ts";

async function patchJson<T>(url: string, body: unknown): Promise<T> {
  const res = await fetch(url, {
    method: "PATCH",
    headers: {"Content-Type": "application/json"},
    body: JSON.stringify(body),
  });

  if (!res.ok) {
    const errorBody: { message?: string } | null = await res.json().catch(() => null);
    throw new Error(errorBody?.message ?? `Request failed: ${res.status}`);
  }

  return res.json();
}

export function ChefProfile() {
  const {user} = useUserContext();
  const chefId = user?.role === "chef" ? user.id : null;

  // Main values
  const [name, setName] = useState("");
  const [phoneNumber, setPhoneNumber] = useState("");
  const [email, setEmail] = useState("");
  const [addresses, setAddresses] = useState<ChefAddressWithDescription[]>([]);

  // Temporary values for editing
  const [tempName, setTempName] = useState("");
  const [tempPhoneNumber, setTempPhoneNumber] = useState("");
  const [tempEmail, setTempEmail] = useState("");
  const [tempAddress, setTempAddress] = useState<ChefAddressWithDescription | null>(null);

  // Modals
  const editProfileDialogRef = useRef<HTMLDialogElement>(null);
  const editAddressDialogRef = useRef<HTMLDialogElement>(null);

  useEffect(() => {
    if (chefId === null) return;

    async function loadChef(id: number) {
      try {
        const res = await fetch(`http://localhost:8100/api/chef/${id}`);
        if (!res.ok) throw new Error(`Chef request failed: ${res.status}`);

        const chef: ChefResponse = await res.json();

        setName(chef.name);
        setEmail(chef.email);
        setPhoneNumber(chef.phoneNumber);
        setAddresses(chef.addresses.map(address => ({
          ...address,
          description: "",
          music: false,
          pets: false,
          outdoor: false,
          familyFriendly: false,
        })));
      } catch (error) {
        console.log(error);
      }
    }

    loadChef(chefId);
  }, [chefId]);

  const handleEditProfileModal = () => {
    setTempName(name);
    setTempPhoneNumber(phoneNumber);
    setTempEmail(email);
    editProfileDialogRef.current?.showModal();
  };

  async function handleSaveChanges() {
    if (chefId === null) return;

    try {
      const updated = await patchJson<ChefResponse>(
        `http://localhost:8100/api/chef/${chefId}`,
        {name: tempName, email: tempEmail, phoneNumber: tempPhoneNumber}
      );

      setName(updated.name);
      setPhoneNumber(updated.phoneNumber);
      setEmail(updated.email);
      editProfileDialogRef.current?.close();
    } catch (error) {
      alert("Fix some react-hot-toast: " + error.message);
    }


  }

  const handleEditAddressModal = (address: ChefAddressWithDescription) => {
    setTempAddress({...address});
    editAddressDialogRef.current?.showModal();
  };

  const handleSaveAddress = async () => {
    if (!tempAddress || chefId === null) return;

    try {
      const updated = await patchJson<ChefAddress>(
        `http://localhost:8100/api/chef/address/${chefId}/${tempAddress.id}`,
        {street: tempAddress.street, postalCode: tempAddress.postalCode, city: tempAddress.city}
      );

      setAddresses(prev => prev.map(a => (a.id === updated.id ? {...a, ...updated} : a)));
      editAddressDialogRef.current?.close();
    } catch (error) {
      alert("Fix some react-hot-toast: " + error.message);
    }
  };

  function updateTempAddress<K extends keyof ChefAddressWithDescription>(
    field: K,
    value: ChefAddressWithDescription[K]
  ) {
    setTempAddress(prev => (prev ? {...prev, [field]: value} : prev));
  }

  return (
    <div className={styles["profile-layout"]}>
      <Banner/>
      <div className={styles["chef-wrapper"]}>
        <div className={styles["chef-top-banner"]}>
          {name ? (<h1>Welcome, {name}!</h1>
          ) : (
            <h1>Welcome!</h1>
          )}

        </div>

        {chefId === null ? (
          <div className={styles["contact-info"]}>
            <p>Not logged in as a chef. Pick a chef on the front page.</p>
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
              <p><strong>Phone number: </strong>{phoneNumber}</p>
            </div>

            <div className={styles["vibe-section-header-banner"]}>
              <h1>Addresses</h1>
            </div>

            {addresses.length === 0 && (
              <div className={styles["contact-info"]}>
                <p>No addresses registered.</p>
              </div>
            )}
            {addresses.map((address) => (
              <div className={styles["contact-info"]} key={address.id}>
                <div className={styles["vibe-section-header-banner"]}>
                  <h3>{address.street}</h3>
                  <button
                    className={styles["edit-profile-description-btn"]}
                    onClick={() => handleEditAddressModal(address)}
                  >
                    Edit address
                  </button>
                </div>
                <ul className={styles["address-list"]}>
                  <li><strong>Street: </strong>{address.street}</li>
                  <li><strong>Postcode: </strong>{address.postalCode}</li>
                  <li><strong>City: </strong>{address.city}</li>
                </ul>
                <p><strong>Description: </strong>{address.description}</p>
                <p><strong>Vibes:</strong></p>
                <ul className={styles["address-list"]}>
                  <li><strong>Music: </strong>{address.music ? "Yes" : "No"}</li>
                  <li><strong>Pets: </strong>{address.pets ? "Yes" : "No"}</li>
                  <li><strong>Outdoor: </strong>{address.outdoor ? "Yes" : "No"}</li>
                  <li><strong>Family Friendly: </strong>{address.familyFriendly ? "Yes" : "No"}</li>
                </ul>
              </div>
            ))}
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
            <div>Phone number:</div>
            <input
              type="text"
              value={tempPhoneNumber}
              onChange={(e) => setTempPhoneNumber(e.target.value)}
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

        <dialog
          className={styles["edit-profile-dialog"]}
          ref={editAddressDialogRef}
        >
          {tempAddress && (
            <>
              <div className={styles["modal-contact-details"]}>
                <div>Street:</div>
                <input
                  type="text"
                  value={tempAddress.street}
                  onChange={(e) => updateTempAddress("street", e.target.value)}
                />
                <div>Postal Code:</div>
                <input
                  type="text"
                  value={tempAddress.postalCode}
                  onChange={(e) => updateTempAddress("postalCode", e.target.value)}
                />
                <div>City:</div>
                <input
                  type="text"
                  value={tempAddress.city}
                  onChange={(e) => updateTempAddress("city", e.target.value)}
                />
                <div>Description:</div>
                <textarea
                  value={tempAddress.description}
                  onChange={(e) => updateTempAddress("description", e.target.value)}
                  rows={3}
                />
                <div className={styles["home-description-container"]}>
                  <label className={styles["label-container"]}>
                    <input
                      type="checkbox"
                      checked={tempAddress.music}
                      onChange={(e) => updateTempAddress("music", e.target.checked)}
                    />
                    Music
                  </label>
                  <label className={styles["label-container"]}>
                    <input
                      type="checkbox"
                      checked={tempAddress.pets}
                      onChange={(e) => updateTempAddress("pets", e.target.checked)}
                    />
                    Pets
                  </label>
                  <label className={styles["label-container"]}>
                    <input
                      type="checkbox"
                      checked={tempAddress.outdoor}
                      onChange={(e) => updateTempAddress("outdoor", e.target.checked)}
                    />
                    Outdoor
                  </label>
                  <label className={styles["label-container"]}>
                    <input
                      type="checkbox"
                      checked={tempAddress.familyFriendly}
                      onChange={(e) => updateTempAddress("familyFriendly", e.target.checked)}
                    />
                    Family Friendly
                  </label>
                </div>
              </div>
              <p>Save changes?</p>
              <div className={styles["save-changes-box"]}>
                <button
                  className={styles["yes-btn"]}
                  onClick={handleSaveAddress}
                >
                  Yes
                </button>
                <button
                  className={styles["no-btn"]}
                  onClick={() => editAddressDialogRef.current?.close()}
                >
                  No
                </button>
              </div>
            </>
          )}
        </dialog>
      </div>
    </div>
  )
}