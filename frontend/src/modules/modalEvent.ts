import { computed } from "vue";

function selectModalColor(data: string): object {
  const modalStyle = computed((): string => {
    return `${data}-modal`;
  });

  return {
    modalStyle,
  };
}

export { selectModalColor };
