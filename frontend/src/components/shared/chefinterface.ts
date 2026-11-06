export interface ChefAddress {
  id: number;
  street: string;
  postalCode: string;
  city: string;
  chefId: number;
}

export interface ChefResponse {
  id: number;
  name: string;
  email: string;
  phoneNumber: string;
  addresses: ChefAddress[];
}

export interface ChefAddressWithDescription extends ChefAddress {
  description: string;
  music: boolean;
  pets: boolean;
  outdoor: boolean;
  familyFriendly: boolean;
}