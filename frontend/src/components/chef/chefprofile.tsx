import {useEffect, useRef, useState} from "react";
import styles from './chefprofile.module.css'
import {Banner} from "../general/banner.tsx";
import {useUserContext} from "../context/useUserContext.tsx";
import {
  type ChefAddress,
  type ChefResponse,
  type Genre,
  type HomeVibe,
  GENRES,
  GENRE_LABELS,
} from "../shared/chefinterface.ts";
import toast from "react-hot-toast";

async function patchJson<T>(url: string, body: unknown): Promise<T> {
  const res = await fetch(url, {
    method: "PATCH",
    headers: {"Content-Type": "application/json"},
    body: JSON.stringify(body),
  });

  if (!res.ok) {
    const errorBody: { detail?: string } | null = await res.json().catch(() => null);
    throw new Error(errorBody?.detail ?? `Request failed: ${res.status}`);
  }

  return res.json();
}

const EMPTY_VIBE: HomeVibe = {
  description: "",
  music: "NONE",
  pets: false,
  outdoor: false,
  familyFriendly: false,
};

export function ChefProfile() {
  const {user} = useUserContext();
  const chefId = user?.role === "chef" ? user.id : null;

  // Main values
  const [name, setName] = useState("");
  const [phoneNumber, setPhoneNumber] = useState("");
  const [email, setEmail] = useState("");
  const [addresses, setAddresses] = useState<ChefAddress[]>([]);

  // Temporary values for editing
  const [tempName, setTempName] = useState("");
  const [tempPhoneNumber, setTempPhoneNumber] = useState("");
  const [tempEmail, setTempEmail] = useState("");
  const [tempAddress, setTempAddress] = useState<ChefAddress | null>(null);
  const [tempVibe, setTempVibe] = useState<HomeVibe>(EMPTY_VIBE);

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
        setAddresses(chef.addresses);
      } catch (error) {
        toast.error(error instanceof Error ? error.message : "Something went wrong");
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
      toast.success("Updated contact info successfully");
      editProfileDialogRef.current?.close();
    } catch (error) {
      toast.error(error instanceof Error ? error.message : "Something went wrong");
    }
  }

  const handleEditAddressModal = (address: ChefAddress) => {
    setTempAddress({...address});
    setTempVibe({...(address.homeVibe ?? EMPTY_VIBE)});
    editAddressDialogRef.current?.showModal();
  };

  const handleSaveAddress = async () => {
    if (!tempAddress || chefId === null) return;

    try {
      const updated = await patchJson<ChefAddress>(
        `http://localhost:8100/api/chef/address/${chefId}/${tempAddress.id}`,
        {
          street: tempAddress.street,
          postalCode: tempAddress.postalCode,
          city: tempAddress.city,
          homeVibe: tempVibe,
        }
      );

      setAddresses(prev => prev.map(a => (a.id === updated.id ? updated : a)));
      toast.success("Updated address successfully");
      editAddressDialogRef.current?.close();
    } catch (error) {
      toast.error(error instanceof Error ? error.message : "Something went wrong");
    }
  };

  function updateTempAddress<K extends keyof ChefAddress>(field: K, value: ChefAddress[K]) {
    setTempAddress(prev => (prev ? {...prev, [field]: value} : prev));
  }

  function updateTempVibe<K extends keyof HomeVibe>(field: K, value: HomeVibe[K]) {
    setTempVibe(prev => ({...prev, [field]: value}));
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
                <p><strong>Description: </strong>{address.homeVibe?.description}</p>
                <p><strong>Vibes:</strong></p>
                <ul className={styles["address-list"]}>
                  <li>
                    <strong>Music: </strong>
                    {address.homeVibe ? GENRE_LABELS[address.homeVibe.music] : "-"}
                  </li>
                  <li><strong>Pets: </strong>{address.homeVibe?.pets ? "Yes" : "No"}</li>
                  <li><strong>Outdoor: </strong>{address.homeVibe?.outdoor ? "Yes" : "No"}</li>
                  <li>
                    <strong>Family Friendly: </strong>
                    {address.homeVibe?.familyFriendly ? "Yes" : "No"}
                  </li>
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
                  value={tempVibe.description}
                  onChange={(e) => updateTempVibe("description", e.target.value)}
                  maxLength={500}
                  rows={3}
                />
                <div>Music:</div>
                <select
                  value={tempVibe.music}
                  onChange={(e) => updateTempVibe("music", e.target.value as Genre)}
                >
                  {GENRES.map(genre => (
                    <option key={genre} value={genre}>{GENRE_LABELS[genre]}</option>
                  ))}
                </select>
                <div className={styles["home-description-container"]}>
                  <label className={styles["label-container"]}>
                    <input
                      type="checkbox"
                      checked={tempVibe.pets}
                      onChange={(e) => updateTempVibe("pets", e.target.checked)}
                    />
                    Pets
                  </label>
                  <label className={styles["label-container"]}>
                    <input
                      type="checkbox"
                      checked={tempVibe.outdoor}
                      onChange={(e) => updateTempVibe("outdoor", e.target.checked)}
                    />
                    Outdoor
                  </label>
                  <label className={styles["label-container"]}>
                    <input
                      type="checkbox"
                      checked={tempVibe.familyFriendly}
                      onChange={(e) => updateTempVibe("familyFriendly", e.target.checked)}
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