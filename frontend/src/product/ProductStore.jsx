import React from "react";
import { LoadingSpinnerContainer } from "../common/LoadingSpinnerContainer";
import { useProductSearch } from "./useProductSearch";
import { ProductFilter } from "./ProductFilter";
import { ProductList } from "./ProductList";

export const ProductStore = () => {
  const {
    data,
    handleChangeFiltering,
    handleChangePagination,
    loading,
    filter,
  } = useProductSearch();
  return (
    <LoadingSpinnerContainer loading={loading}>
      <div>
        <ProductFilter
          handleChangeFiltering={handleChangeFiltering}
          filter={filter}
        />
        <ProductList
          handleChangePagination={handleChangePagination}
          handleClickFilterLink={handleChangeFiltering}
          data={data}
        />
      </div>
    </LoadingSpinnerContainer>
  );
};
