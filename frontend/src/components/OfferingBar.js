import { forwardRef } from 'react';
import { createStyles, NumberInput, rem } from '@mantine/core';

const useStyles = createStyles((theme) => ({
  wrapper: {
    position: 'relative',
  },

  input: {
    height: 'auto',
    paddingTop: rem(22),
    paddingBottom: rem(3),
    borderBottomRightRadius: 0,
    borderBottomLeftRadius: 0,
  },

  label: {
    position: 'absolute',
    pointerEvents: 'none',
    paddingLeft: theme.spacing.sm,
    paddingTop: `calc(${theme.spacing.sm} / 2)`,
    zIndex: 1,
  },

  thumb: {
    width: rem(16),
    height: rem(16),
  },

  track: {
    backgroundColor: theme.colorScheme === 'dark' ? theme.colors.dark[3] : theme.colors.gray[4],
  },
}));

const SliderInput = forwardRef((props, ref) => {
  const { classes } = useStyles();
  // const [value, setValue] = useState(50);

  return (
    <div className={classes.wrapper}>
      <NumberInput
        ref={ref}
        value={props.value}
        // onChange={setValue}
        label={props.label}
        placeholder="Twoja wysokość datku"
        defaultValue={50}
        step={10}
        min={props.minimalOffering}
        max={10434}
        classNames={{ input: classes.input, label: classes.label }}
      />
    </div>
  );
});

export default SliderInput;
